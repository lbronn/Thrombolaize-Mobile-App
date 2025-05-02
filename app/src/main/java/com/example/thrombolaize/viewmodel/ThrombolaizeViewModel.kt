package com.example.thrombolaize.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thrombolaize.model.AIPredictionResponse
import com.example.thrombolaize.model.PatientDetails
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.storage
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.security.MessageDigest

class ThrombolaizeViewModel: ViewModel() {
    private val patientDetailsFetched = MutableStateFlow<List<PatientDetails>>(emptyList())
    private val predictionResultsFetched = MutableStateFlow<List<AIPredictionResponse>>(emptyList())
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firebaseUser = auth.currentUser
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val firebaseStorage = Firebase.storage
    private val fetchedCTScansURL = MutableStateFlow<String?>(null)
    val currentCTScanURL = fetchedCTScansURL.asStateFlow()
    val currentPatientDetails = patientDetailsFetched.asStateFlow()
    val currentPredictionResults = predictionResultsFetched.asStateFlow()

    init {
        fetchAllPatientDetails()
        fetchAllPredictions()
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

    fun savePatientDetails(patientAge: String, patientSex: String, strokeOnset: String, timeOfArrival: String, onResult: (Boolean, String?, Long?) -> Unit) {
        val currentUser = FirebaseAuth.getInstance().currentUser?.uid ?: return

        val patientCTScanURL = fetchedCTScansURL.value
        if (patientAge.isBlank() || patientSex.isBlank() || strokeOnset.isBlank() || timeOfArrival.isBlank() || patientCTScanURL == null) {
            onResult(false, "Please complete all fields and upload a CT scan.", null)
            return
        }

        val createdAt = System.currentTimeMillis()
        val patientDetailsCreated = createdAt.toString()

        val newUserDetails = PatientDetails(
            uid = currentUser,
            patientAge = patientAge,
            patientSex = patientSex,
            strokeOnset = strokeOnset,
            timeOfArrival = timeOfArrival,
            patientCTScanURL = patientCTScanURL,
            createdAt = createdAt
        )

        viewModelScope.launch {
            try {
                val patientDetailsCollection = firestore.collection("patientDetails").document(patientDetailsCreated)
                    .get().await()

                if (patientDetailsCollection.exists()) {
                    val updatePatientDetails = mutableMapOf<String, Any>().apply {
                        put("patientAge", patientAge)
                        put("patientSex", patientSex)
                        put("strokeOnset", strokeOnset)
                        put("timeOfArrival", timeOfArrival)
                        put("patientCTScanURL", patientCTScanURL)
                    }
                    firestore.collection("patientDetails")
                        .document(patientDetailsCreated)
                        .update(updatePatientDetails)
                        .await()
                    onResult(true, "Patient information updated successfully.", createdAt)
                } else {
                    firestore
                        .collection("patientDetails").document(patientDetailsCreated)
                        .set(newUserDetails).await()

                    fetchAllPatientDetails()
                    onResult(true, "Successfully saved patient's basic information.", createdAt)
                    fetchAndStorePrediction(patientCTScanURL, createdAt)
                }
            } catch (e: Exception) {
                Log.e("SavePatientDetails", "Error saving patient details", e)
                onResult(false, e.localizedMessage, null)
            }
        }
    }

    fun clearCTScan() {
        fetchedCTScansURL.value = null
    }

    private fun fetchAllPatientDetails() {
        val currentUser = FirebaseAuth.getInstance().currentUser?.uid ?: return

        viewModelScope.launch {
            try {
                val docs = firestore
                    .collection("patientDetails")
                    .whereEqualTo("uid", currentUser)
                    .get().await()
                patientDetailsFetched.value = docs.map { it.toObject(PatientDetails::class.java) }
            } catch (e: Exception) {
                Log.e("FetchPatientDetails", "Error fetching patient details", e)
            }
        }
    }

    private fun fetchAllPredictions() {
        val currentUser = FirebaseAuth.getInstance().currentUser?.uid ?: return

        viewModelScope.launch {
            try {
                val docs = firestore
                    .collection("predictions")
                    .whereEqualTo("uid", currentUser)
                    .get().await()
                predictionResultsFetched.value = docs.map { it.toObject(AIPredictionResponse::class.java) }
            } catch (e: Exception) {
                Log.e("FetchPredictionDetails", "Error fetching prediction details", e)
            }
        }
    }

    private fun String.toFirestoreId(): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(this.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    private suspend fun fetchAndStorePrediction(ctUrl: String, createdAt: Long) = withContext(Dispatchers.IO) {
        val currentUser = FirebaseAuth.getInstance().currentUser?.uid ?: return@withContext

        val client = OkHttpClient()
        val body = """{ "url":"$ctUrl" }""".toRequestBody("application/json; charset=utf-8".toMediaType())
        val resp = client.newCall(Request.Builder().url("http://20.78.8.56:6969/predict")
            .post(body)
            .build())
            .execute()

        val json = resp.body?.string() ?: throw IOException("Empty response")
        if (!resp.isSuccessful) throw IOException("HTTP ${resp.code}")

        val base = Gson().fromJson(json, AIPredictionResponse::class.java)
        val full = currentUser.let {
            base.copy(
                ctUrl = ctUrl,
                createdAt = createdAt,
                uid = currentUser
            )
        }

        val docId = ctUrl.toFirestoreId()
        firestore.collection("predictions").document(docId).set(full).await()

        Log.d("PredictionStorage", "Stored under $docId → $full")
        fetchAllPredictions()
    }
}