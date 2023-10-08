package ru.kraz.chat.domain.chat

import ru.kraz.chat.domain.ResultApi

class SendMessageUseCase(
    private val repository: ChatRepository,
) {
    suspend operator fun invoke(message: String){
        repository.sendMessage(message)
    }
}