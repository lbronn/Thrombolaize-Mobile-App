package com.example.thrombolaize.model

data class Chat(
    val chatID: String = "",
    val chatCreated: Long = System.currentTimeMillis(),
    val chatContent: String = "",
    val senderID: String = "",
    val senderName: String = "",
    val receiverID: String = "",
    val receiverName: String = ""
)
