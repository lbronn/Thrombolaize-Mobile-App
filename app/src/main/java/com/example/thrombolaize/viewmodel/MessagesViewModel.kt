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
    private val firebaseDB = Firebase.database("https://thrombolaize-3f3a0.firebaseio.com/")
    private val messagesFetched = MutableStateFlow<List<Message>>(emptyList())
    val currentMessages = messagesFetched.asStateFlow()

    init {
        getMessages()
    }

    private fun getMessages() {
        if (currentUser.isNotEmpty()) {
            firebaseDB.getReference("messages")
                .get().addOnSuccessListener { snapshot ->
                    val list = mutableListOf<Message>()
                    snapshot.children.forEach { data ->
                        val message = data.getValue(Message::class.java)
                        if (message != null && message.messageName.isNotEmpty() && message.senderID == currentUser) {
                            Log.d("MessageDebug", "Fetched message with senderID: ${message.senderID} (currentUser: $currentUser)")
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


    fun addMessages(messageName: String) {
        if (currentUser.isNotEmpty()) {
            val addMessage = firebaseDB.getReference("messages").push().key
            val newMessage = Message(
                messageID = addMessage ?: "",
                messageName = messageName,
                messageCreated = System.currentTimeMillis(),
                senderID = currentUser
            )
            firebaseDB.getReference("messages").child(addMessage!!).setValue(newMessage).addOnSuccessListener {
                getMessages()
            }
        }
    }
}