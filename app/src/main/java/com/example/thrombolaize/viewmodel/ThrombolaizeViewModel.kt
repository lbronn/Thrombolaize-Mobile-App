package com.example.thrombolaize.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thrombolaize.model.PatientDetails
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ThrombolaizeViewModel: ViewModel() {
    private val patientDetailsFetched = MutableStateFlow<List<PatientDetails>>(emptyList())
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firebaseUser = auth.currentUser
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val firebaseStorage = Firebase.storage
    private val fetchedCTScansURL = MutableStateFlow<String?>(null)
    val currentCTScanURL = fetchedCTScansURL.asStateFlow()

    init {
        fetchAllPatientDetails()
    }

    fun uploadPatientCT(imageUri: Uri) {
        val fileRef = firebaseStorage.reference.child("ct-scan_images/${firebaseUser?.uid}/${System.currentTimeMillis()}.jpg")

        viewModelScope.launch {
            try {
                fileRef.putFile(imageUri).await()
                val downloadUrl = fileRef.downloadUrl.await().toString()
                fetchedCTScansURL.value = downloadUrl
                Log.d("CTScanURL", downloadUrl)
            } catch (e: Exception) {
                Log.e("StorageFetchError", e.printStackTrace().toString())
            }
        }
    }

    fun savePatientDetails(patientAge: String, patientSex: String, strokeOnset: String, timeOfArrival: String, onResult: (Boolean, String?) -> Unit) {
        val userUID = firebaseUser?.uid
        if (userUID == null) {
            onResult(false, "No logged in user.")
            return
        }

        val patientCTScanURL = fetchedCTScansURL.value

        if (patientAge.isBlank() || patientSex.isBlank() || strokeOnset.isBlank() || timeOfArrival.isBlank() || patientCTScanURL == null) {
            onResult(false, "Please complete all fields and upload a CT scan.")
            return
        }

        val newUserDetails = PatientDetails(
            uid = userUID,
            patientAge = patientAge,
            patientSex = patientSex,
            strokeOnset = strokeOnset,
            timeOfArrival = timeOfArrival,
            patientCTScanURL = patientCTScanURL
        )

        firestore.collection("patientDetails").document(userUID).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val updatePatientDetails = mutableMapOf<String, Any>()
                    if (patientAge.isNotBlank()) updatePatientDetails["patientAge"] = patientAge
                    if (patientSex.isNotBlank()) updatePatientDetails["patientSex"] = patientSex
                    if (strokeOnset.isNotBlank()) updatePatientDetails["strokeOnset"] = strokeOnset
                    if (timeOfArrival.isNotBlank()) updatePatientDetails["timeOfArrival"] = timeOfArrival

                    val updatedCTScanURL = fetchedCTScansURL.value ?: ""
                    if (updatedCTScanURL.isNotBlank()) {
                        updatePatientDetails["patientCTScanURL"] = updatedCTScanURL
                    }

                    if (updatePatientDetails.isEmpty()) {
                        onResult(false, "No updates on the patient's profile.")
                        return@addOnSuccessListener
                    }

                    firestore.collection("patientDetails").document(userUID)
                        .update(updatePatientDetails)
                        .addOnSuccessListener { onResult(true, "Patient's basic information is updated successfully.") }
                        .addOnFailureListener { exception ->
                            onResult(false, "Failed to update patient details: ${exception.localizedMessage}")
                        }
                } else {
                    firestore.collection("patientDetails").document(userUID)
                        .set(newUserDetails)
                        .addOnSuccessListener { onResult(true, "Successfully saved patient's basic information.") }
                        .addOnFailureListener { onResult(false, "Failed to save patient's basic information.") }
                }
            }
            .addOnFailureListener { exception ->
                onResult(false, "Error checking patient details: ${exception.localizedMessage}")
            }
    }

    fun clearCTScan() {
        fetchedCTScansURL.value = null
    }

    private fun fetchAllPatientDetails() {
        firestore.collection("patientDetails")
            .get()
            .addOnSuccessListener { documents ->
                val list = documents.mapNotNull {
                    it.toObject(PatientDetails::class.java)
                }
                patientDetailsFetched.value = list
                list.forEach { document ->
                    val uid = document.uid
                    val patientAge = document.patientAge
                    val patientSex = document.patientSex
                    val strokeOnset = document.strokeOnset
                    val timeOfArrival = document.timeOfArrival
                    val patientCTScanURL = document.patientCTScanURL
                    Log.d("PatientDetails", "Fetched patient: $uid, $patientAge, $patientSex, " +
                            "$strokeOnset, $timeOfArrival ,$patientCTScanURL")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("PatientDetailsError", "Error fetching users: ${exception.localizedMessage}")
            }
    }
}