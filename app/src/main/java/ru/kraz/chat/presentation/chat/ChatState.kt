package ru.kraz.chat.presentation.chat

import ru.kraz.chat.presentation.Comparing
import java.util.Date

sealed class MessageUi : Comparing<MessageUi> {

    override fun same(item: MessageUi): Boolean = false

    override fun sameContent(item: MessageUi): Boolean = false

    data class Sender(
        val senderId: String,
        val senderNickname: String,
        val message: String,
        val photo: String,
        val isCurrentUser: Boolean,
        val createdDate: Date,
    ) : MessageUi() {
        override fun same(item: MessageUi): Boolean =
            item is Sender && senderId == item.senderId && createdDate == item.createdDate

        override fun sameContent(item: MessageUi): Boolean = this == item
    }

    data class Recipient(
        val senderId: String,
        val senderNickname: String,
        val message: String,
        val photo: String,
        val isCurrentUser: Boolean,
        val createdDate: Date,
    ) : MessageUi() {
        override fun same(item: MessageUi): Boolean =
            item is Recipient && senderId == item.senderId && createdDate == item.createdDate

        override fun sameContent(item: MessageUi): Boolean = this == item
    }

    data class Error(val msg: String): MessageUi()
}

sealed class ChatState {

    data class Success(val data: List<MessageUi>) : ChatState()
    data object Loading : ChatState()

    data class Error(val msg: String) : ChatState()
}
