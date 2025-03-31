package com.example.thrombolaize.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.thrombolaize.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserAuthenticationViewModel: ViewModel() {
    private var user by mutableStateOf<User?>(null)
    val currentUser: User? get() = user
    private var loginError by mutableStateOf<String?>(null)
    private var isLoading by mutableStateOf(false)
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        fetchLoggedInUser()
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
                    firebaseUser?.let {
                        getNextUserId { newUserId ->
                            val newUser = User(userID = newUserId, firstName = firstName, lastName = lastName, specialty = specialty, email = email)
                            saveUserToFirestore(newUser, onResult)
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

    fun fetchLoggedInUser() {
        val firebaseUser = auth.currentUser
        if (firebaseUser != null && user == null) {
            fetchUserFromFirestore(firebaseUser.email ?: "") { _, _ ->
            }
        }
    }
}