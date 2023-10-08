package ru.kraz.chat.domain.chat

import java.util.Date

data class MessageDomain(
    val senderId: String,
    val senderNickname: String,
    val message: String,
    val photo: String,
    val isCurrentUser: Boolean,
    val createdDate: Date
)