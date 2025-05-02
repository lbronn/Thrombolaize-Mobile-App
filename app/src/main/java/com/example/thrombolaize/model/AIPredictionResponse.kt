package com.example.thrombolaize.model

data class AIPredictionResponse(
    val ischemic: String = "",
    val hemorrhagic: String = "",
    val normal: String = "",
    val predicted: String = "",
    val ischemickey: String = "",
    val ctUrl: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val uid: String = ""
)
