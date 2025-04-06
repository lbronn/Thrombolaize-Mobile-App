package com.example.thrombolaize.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thrombolaize.model.UserDetails
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UserProfileViewModel: ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firebaseUser = auth.currentUser
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val firebaseStorage = Firebase.storage
    private val fetchedPicturesURL = MutableStateFlow<String?>(null)
    val currentPictureURL = fetchedPicturesURL.asStateFlow()

    fun uploadProfile(imageUri: Uri) {
        val fileRef = firebaseStorage.reference.child("profile_images/${System.currentTimeMillis()}.jpg")
        viewModelScope.launch {
            try {
                fileRef.putFile(imageUri).await()
                val downloadUrl = fileRef.downloadUrl.await().toString()
                fetchedPicturesURL.value = downloadUrl
            } catch (e: Exception) {
                Log.e("StorageFetchError", e.printStackTrace().toString())
            }
        }
    }

    fun addUserDetails(aboutUser: String, phoneNumber: String, workSchedule: String, hospitals: String, affiliations: String, onResult: (Boolean, String?) -> Unit) {
        if (aboutUser.isBlank() || phoneNumber.isBlank() || workSchedule.isBlank() || hospitals.isBlank() || affiliations.isBlank()) {
            onResult(false, "All information are required.")
            return
        }

        val newUserDetails = firebaseUser?.let { user ->
            UserDetails(
                uid = user.uid,
                aboutUser = aboutUser,
                phoneNumber = phoneNumber,
                workSchedule = workSchedule,
                hospitals = hospitals,
                affiliations = affiliations
            )
        }

        if (newUserDetails != null) {
            saveUserDetails(newUserDetails, onResult)
        }
    }

    private fun saveUserDetails(userDetails: UserDetails, onResult: (Boolean, String?) -> Unit) {
        firestore.collection("userDetails").document(userDetails.uid)
            .set(userDetails)
            .addOnSuccessListener { onResult(true, "Successfully saved user information.") }
            .addOnFailureListener { onResult(false, "Failed to save user information.") }
    }
}