package com.example.thrombolaize.model

data class Message (
    val messageID: String = "",
    val messageName: String = "",
    val messageCreated: Long = System.currentTimeMillis(),
    val senderID: String = ""
)