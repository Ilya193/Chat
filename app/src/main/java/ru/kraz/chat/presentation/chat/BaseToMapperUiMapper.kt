package ru.kraz.chat.presentation.chat

import ru.kraz.chat.domain.ToUiMapper
import ru.kraz.chat.domain.chat.MessageDomain

class BaseToMapperUiMapper : ToUiMapper<MessageDomain, MessageUi> {
    override fun map(data: MessageDomain): MessageUi {
        return if (data.isCurrentUser)
            MessageUi.Sender(
                data.senderId,
                data.senderNickname,
                data.message,
                data.photo,
                data.isCurrentUser,
                data.createdDate
            )
        else MessageUi.Recipient(
            data.senderId,
            data.senderNickname,
            data.message,
            data.photo,
            data.isCurrentUser,
            data.createdDate
        )
    }
}