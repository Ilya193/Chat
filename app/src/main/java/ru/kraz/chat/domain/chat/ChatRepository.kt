package ru.kraz.chat.domain.chat

import kotlinx.coroutines.flow.Flow
import ru.kraz.chat.domain.ResultApi

interface ChatRepository {
    val error: String
        get() = "Something went wrong"

    suspend fun sendMessage(message: String)
    suspend fun sendMessageWithImage(message: String, filename: String, imgUri: String)
    suspend fun fetchMessage(): Flow<ResultApi<List<MessageDomain>>>
}