package com.example.thrombolaize.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.thrombolaize.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserAuthenticationViewModel: ViewModel() {
    private var user by mutableStateOf<User?>(null)
    val currentUser: User? get() = user
    private var loginError by mutableStateOf<String?>(null)
    private var isLoading by mutableStateOf(false)
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val currentUserName: String
        get() = auth.currentUser?.displayName ?: ""
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val usersFetched = MutableStateFlow<List<User>>(emptyList())
    val currentUsers = usersFetched.asStateFlow()

    init {
        fetchLoggedInUser()
        fetchAllUserDisplayNames()
    }

    fun registerUser(firstName: String, lastName: String, specialty: String, email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        if (firstName.isBlank() || lastName.isBlank() || specialty.isBlank() || email.isBlank() || password.isBlank()) {
            onResult(false, "Invalid Credentials!")
            return
        }

        isLoading = true
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                isLoading = false
                if (task.isSuccessful) {
                    val firebaseUser = auth.currentUser
                    firebaseUser?.let { user ->
                        val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName("$firstName $lastName").build()
                        user.updateProfile(profileUpdates)
                            .addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    getNextUserId { newUserId ->
                                        val newUser = User(
                                            uid = user.uid,
                                            userID = newUserId,
                                            firstName = firstName,
                                            lastName = lastName,
                                            specialty = specialty,
                                            email = email)
                                        saveUserToFirestore(newUser, onResult)
                                    }
                                } else {
                                    onResult(false, "Failed to update the display name with the user's first and last names.")
                                }
                            }
                    }
                } else {
                    loginError = task.exception?.message
                    onResult(false, null)
                }
            }
    }

    fun loginUser(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            onResult(false, "Email or password must not be empty!")
            return
        }

        firestore.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    onResult(false, "Email is not registered!")
                } else {
                    isLoading = true
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            isLoading = false
                            if (task.isSuccessful) {
                                auth.currentUser?.let { firebaseUser ->
                                    fetchUserFromFirestore(firebaseUser.email ?: "", onResult)
                                }
                            } else {
                                loginError = task.exception?.message
                                onResult(false, "Invalid Credentials!")
                            }
                        }
                }
            }
            .addOnFailureListener { exception ->
                onResult(false, exception.localizedMessage ?: "Error verifying email!")
            }
    }

    fun logout() {
        auth.signOut()
        user = null
    }

    private fun getNextUserId(onResult: (Int) -> Unit) {
        firestore.collection("users")
            .orderBy("userID")
            .limitToLast(1)
            .get()
            .addOnSuccessListener { documents ->
                val lastUserId = if (documents.isEmpty) 0 else documents.documents.first().getLong("userID")?.toInt() ?: 0
                onResult(lastUserId + 1)
            }
            .addOnFailureListener {
                onResult(1)
            }
    }

    private fun saveUserToFirestore(user: User, onResult: (Boolean, String?) -> Unit) {
        firestore.collection("users").document(user.userID.toString())
            .set(user)
            .addOnSuccessListener { onResult(true, null) }
            .addOnFailureListener { onResult(false, null) }
    }

    private fun fetchUserFromFirestore(email: String, onResult: (Boolean, String?) -> Unit) {
        firestore.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val userData = documents.documents.first().toObject(User::class.java)
                    user = userData
                    onResult(true, null)
                } else {
                    onResult(false, "Invalid Credentials!")
                }
            }
            .addOnFailureListener {
                onResult(false, null)
            }
    }

    private fun fetchAllUserDisplayNames() {
        firestore.collection("users")
            .get()
            .addOnSuccessListener { documents ->
                val list = documents.mapNotNull {
                    it.toObject(User::class.java)
                }
                usersFetched.value = list
                list.forEach { document ->
                    val name = "${document.firstName} ${document.lastName}"
                    Log.d("UserDisplayName", "Fetched user: $name")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("UserDisplayName", "Error fetching users: ${exception.localizedMessage}")
            }
    }

    fun fetchLoggedInUser() {
        val firebaseUser = auth.currentUser
        if (firebaseUser != null && user == null) {
            fetchUserFromFirestore(firebaseUser.email ?: "") { _, _ ->
            }
        }
    }
}