package ru.kraz.chat.data.chat

import kotlinx.coroutines.flow.Flow
import ru.kraz.chat.domain.ResultApi

interface ChatCloudDataSource {
    suspend fun sendMessage(message: String)
    suspend fun sendMessageWithImage(message: String, filename: String, imgUri: String)
    suspend fun fetchMessage(): Flow<List<MessageCloud>>
}