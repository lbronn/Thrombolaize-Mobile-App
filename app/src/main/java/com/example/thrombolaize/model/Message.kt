package com.example.thrombolaize.model

data class Message (
    val messageID: String = "",
    val receiverID: String = "",
    val receiverName: String = "",
    val messageCreated: Long = System.currentTimeMillis(),
    val senderID: String = "",
    val senderName: String = ""
)