package ru.kraz.chat.data.chat

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class MessageCloud(
    val senderId: String = "",
    val senderNickname: String = "",
    val message: String = "",
    val photo: String = "",
    @ServerTimestamp
    val createdDate: Date = Date(),
) {
    fun map(id: String): MessageData =
        MessageData(senderId, senderNickname, message, photo, id == senderId, createdDate)
}
