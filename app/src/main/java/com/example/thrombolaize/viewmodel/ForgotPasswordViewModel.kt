package com.example.thrombolaize.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ForgotPasswordViewModel: ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun forgotPassword(email: String, onResult: (Boolean, String?) -> Unit) {
        if (email.isBlank()) {
            onResult(false, "Email must not be empty!")
            return
        }

        db.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                if (documents != null && !documents.isEmpty) {
                    auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                onResult(true, null)
                            } else {
                                val errorMessage = task.exception?.localizedMessage ?: "Unknown error occurred."
                                onResult(false, errorMessage)
                            }
                        }
                } else {
                    onResult(false, "Email is not registered.")
                }
            }
            .addOnFailureListener { exception ->
                onResult(false, exception.localizedMessage ?: "Failed to verify email.")
            }
    }
}