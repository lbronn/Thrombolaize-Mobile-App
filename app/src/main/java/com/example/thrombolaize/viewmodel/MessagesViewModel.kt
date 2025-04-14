package com.example.thrombolaize.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.thrombolaize.model.Message
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor() : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val currentUser: String
        get() = auth.currentUser?.uid ?: ""
    private val currentUserName: String
        get() = auth.currentUser?.displayName ?: ""
    private val firebaseDB = Firebase.database("https://thrombolaize-3f3a0.firebaseio.com/")
    private val messagesFetched = MutableStateFlow<List<Message>>(emptyList())
    val currentMessages = messagesFetched.asStateFlow()

    init {
        getMessages()
    }

    private fun getMessages() {
        if (currentUser.isNotEmpty()) {
            firebaseDB.getReference("messages").get().addOnSuccessListener { snapshot ->
                val list = mutableListOf<Message>()
                snapshot.children.forEach { data ->
                    val message = data.getValue(Message::class.java)
                    if (message != null &&
                        message.receiverName.isNotEmpty() &&
                        (message.senderID == currentUser || message.receiverID == currentUser)
                    ) {
                        Log.d("MessageDebug", "Fetched messages of senderID ${message.senderID}")
                        list.add(message)
                    }
                }
                list.sortByDescending { it.messageCreated }
                messagesFetched.value = list
            }
        } else {
            messagesFetched.value = emptyList()
        }
    }

    fun addMessages(receiverID: String, receiverName: String) {
        if (currentUser.isNotEmpty()) {
            val addMessage = firebaseDB.getReference("messages").push().key
            val newMessage = Message(
                messageID = addMessage ?: "",
                receiverID = receiverID,
                receiverName = receiverName,
                messageCreated = System.currentTimeMillis(),
                senderID = currentUser,
                senderName = currentUserName
            )
            firebaseDB.getReference("messages").child(addMessage!!).setValue(newMessage).addOnSuccessListener {
                getMessages()
            }
        }
    }

    fun deleteMessages(messageID: String) {
        if (currentUser.isNotEmpty()) {
            firebaseDB.getReference("messages").child(messageID).removeValue()
        }
    }
}