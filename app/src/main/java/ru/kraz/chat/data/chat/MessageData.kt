package ru.kraz.chat.data.chat

import ru.kraz.chat.domain.ToMapper
import ru.kraz.chat.domain.chat.MessageDomain
import java.util.Date

data class MessageData(
    val senderId: String,
    val senderNickname: String,
    val message: String,
    val photo: String,
    val isCurrentUser: Boolean,
    val createdDate: Date
) : ToMapper<MessageDomain> {
    override fun map(): MessageDomain =
        MessageDomain(senderId, senderNickname, message, photo, isCurrentUser, createdDate)

}