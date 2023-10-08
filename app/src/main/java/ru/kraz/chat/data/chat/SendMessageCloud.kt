package ru.kraz.chat.data.chat

data class SendMessageCloud(
    val to: String = "",
    val notification: MessageNotification,
    val data:  MessageDataNotification
)

data class MessageNotification(
    val title: String = "",
    val body: String = ""
)

data class MessageDataNotification(
    val senderId: String
)