package com.example.thrombolaize.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.thrombolaize.model.Chat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor() : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val currentUserID: String
        get() = auth.currentUser?.uid ?: ""
    private val currentUserName: String
        get() = auth.currentUser?.displayName ?: ""
    private val firebaseDB = Firebase.database("https://thrombolaize-3f3a0.firebaseio.com/")
    private val chatsFetched = MutableStateFlow<List<Chat>>(emptyList())
    val currentChats = chatsFetched.asStateFlow()

    fun chatsListener(messageID: String) {
        firebaseDB.getReference("chats").child(messageID).orderByChild("chatCreated")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<Chat>()
                    snapshot.children.forEach { data ->
                        val chat = data.getValue(Chat::class.java)
                        if (chat != null && chat.chatContent.isNotEmpty()) {
                            list.add(chat)
                            Log.d("ChatsDebug", "Fetched chats from ${chat.senderName} to ${chat.receiverName} ")
                        }
                    }
                    chatsFetched.value = list
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("ChatsDebug", "Error fetching chats: ${error.message}")
                }
            })
    }

    fun sendChats(messageID: String, chatContent: String, receiverID: String, receiverName: String) {
        val newChat = Chat(
            chatID = firebaseDB.reference.push().key.toString(),
            chatCreated = System.currentTimeMillis(),
            chatContent = chatContent,
            senderID = currentUserID,
            senderName = currentUserName,
            receiverID = receiverID,
            receiverName = receiverName
        )
        firebaseDB.reference.child("chats").child(messageID).push().setValue(newChat)
    }

    fun removeChats(messageID: String) {
        firebaseDB.getReference("chats").child(messageID).removeValue()
    }
}