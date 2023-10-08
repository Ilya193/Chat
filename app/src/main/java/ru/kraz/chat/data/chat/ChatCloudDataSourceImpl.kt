package ru.kraz.chat.data.chat

import android.content.Context
import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ChatCloudDataSourceImpl(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage,
    private val context: Context,
    private val service: ChatService,
) : ChatCloudDataSource {
    override suspend fun sendMessage(message: String) {
        val id = auth.currentUser?.uid!!
        val nickname = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
            .getString("nickname", "") ?: ""
        val messageCloud = MessageCloud(id, nickname, message)

        firestore.collection("messages")
            .document()
            .set(messageCloud)
            .await()

        val messageNotification =
            SendMessageCloud(
                "/topics/messages",
                MessageNotification(nickname, message),
                MessageDataNotification(id)
            )
        service.sendMessage(messageNotification)
    }

    override suspend fun sendMessageWithImage(message: String, filename: String, imgUri: String) {
        val id = auth.currentUser?.uid!!
        val nickname = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
            .getString("nickname", "") ?: ""

        val storageReference = storage.reference.child("images/$filename")
        storageReference.putFile(Uri.parse(imgUri)).await()
        val url = storageReference.downloadUrl.await()
        val messageCloud = MessageCloud(id, nickname, message, url.toString())

        firestore.collection("messages")
            .document()
            .set(messageCloud)
            .await()

        val messageNotification =
            SendMessageCloud(
                "/topics/messages",
                MessageNotification(nickname, message),
                MessageDataNotification(id)
            )
        service.sendMessage(messageNotification)
    }

    override suspend fun fetchMessage(): Flow<List<MessageCloud>> = callbackFlow {
        val listener = firestore.collection("messages")
            .orderBy("createdDate", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e == null) {
                    val list = mutableListOf<MessageCloud>()
                    for (item in snapshot!!.documents) {
                        val messageCloud = item.toObject(MessageCloud::class.java)!!
                        list.add(messageCloud)
                    }
                    trySend(list)
                }
            }

        awaitClose {
            listener.remove()
        }
    }
}