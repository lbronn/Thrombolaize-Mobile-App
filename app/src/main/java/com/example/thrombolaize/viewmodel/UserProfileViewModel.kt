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
    private val userDetailsFetched = MutableStateFlow<List<UserDetails>>(emptyList())
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firebaseUser = auth.currentUser
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val firebaseStorage = Firebase.storage
    private val fetchedPicturesURL = MutableStateFlow<String?>(null)
    val currentPictureURL = fetchedPicturesURL.asStateFlow()
    val currentUserDetails = userDetailsFetched.asStateFlow()

    init {
        fetchAllUserDetails()
    }

    fun uploadProfile(imageUri: Uri) {
        val fileRef = firebaseStorage.reference.child("profile_images/${firebaseUser?.uid}/${System.currentTimeMillis()}.jpg")

        viewModelScope.launch {
            try {
                fileRef.putFile(imageUri).await()
                val downloadUrl = fileRef.downloadUrl.await().toString()
                fetchedPicturesURL.value = downloadUrl
                Log.d("PictureURL", downloadUrl)
            } catch (e: Exception) {
                Log.e("StorageFetchError", e.printStackTrace().toString())
            }
        }
    }

    fun saveUserDetails(aboutUser: String, phoneNumber: String, workSchedule: String, hospital: String, extraHospital: String, affiliation: String, extraAffiliation: String, onResult: (Boolean, String?) -> Unit) {
        val userUID = firebaseUser?.uid
        if (userUID == null) {
            onResult(false, "No logged in user.")
            return
        }

        val userProfilePicURL = fetchedPicturesURL.value ?: ""
        val newUserDetails = UserDetails(
            uid = userUID,
            aboutUser = aboutUser,
            phoneNumber = phoneNumber,
            workSchedule = workSchedule,
            hospital = hospital,
            extraHospital = extraHospital,
            affiliation = affiliation,
            extraAffiliation = extraAffiliation,
            userProfilePicURL = userProfilePicURL
        )

        firestore.collection("userDetails").document(userUID).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val updateUserDetails = mutableMapOf<String, Any>()
                    if (aboutUser.isNotBlank()) updateUserDetails["aboutUser"] = aboutUser
                    if (phoneNumber.isNotBlank()) updateUserDetails["phoneNumber"] = phoneNumber
                    if (workSchedule.isNotBlank()) updateUserDetails["workSchedule"] = workSchedule
                    if (hospital.isNotBlank()) updateUserDetails["hospital"] = hospital
                    if (extraHospital.isNotBlank()) updateUserDetails["extraHospital"] = extraHospital
                    if (affiliation.isNotBlank()) updateUserDetails["affiliation"] = affiliation
                    if (extraAffiliation.isNotBlank()) updateUserDetails["extraAffiliation"] = extraAffiliation

                    val updatedProfilePicURL = fetchedPicturesURL.value ?: ""
                    if (updatedProfilePicURL.isNotBlank()) {
                        updateUserDetails["userProfilePicURL"] = updatedProfilePicURL
                    }

                    if (updateUserDetails.isEmpty()) {
                        onResult(false, "No updates on the user's profile.")
                        return@addOnSuccessListener
                    }

                    firestore.collection("userDetails").document(userUID)
                        .update(updateUserDetails)
                        .addOnSuccessListener { onResult(true, "User profile is updated successfully.") }
                        .addOnFailureListener { exception ->
                            onResult(false, "Failed to update user details: ${exception.localizedMessage}")
                        }
                } else {
                    firestore.collection("userDetails").document(userUID)
                        .set(newUserDetails)
                        .addOnSuccessListener { onResult(true, "Successfully saved user information.") }
                        .addOnFailureListener { onResult(false, "Failed to save user information.") }
                }
            }
            .addOnFailureListener { exception ->
                onResult(false, "Error checking user details: ${exception.localizedMessage}")
            }
    }

    private fun fetchAllUserDetails() {
        firestore.collection("userDetails")
            .get()
            .addOnSuccessListener { documents ->
                val list = documents.mapNotNull {
                    it.toObject(UserDetails::class.java)
                }
                userDetailsFetched.value = list
                list.forEach { document ->
                    val uid = document.uid
                    val aboutUser = document.aboutUser
                    val phone = document.phoneNumber
                    val workSchedule = document.workSchedule
                    val hospital = document.hospital
                    val extraHospital = document.extraHospital
                    val affiliation = document.affiliation
                    val extraAffiliation = document.extraAffiliation
                    val userProfilePicURL = document.userProfilePicURL
                    Log.d("UserDetails", "Fetched user: $uid, $aboutUser, $phone, $workSchedule, " +
                                "$hospital, $affiliation, $extraHospital, $extraAffiliation, $userProfilePicURL")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("UserDisplayName", "Error fetching users: ${exception.localizedMessage}")
            }
    }
}