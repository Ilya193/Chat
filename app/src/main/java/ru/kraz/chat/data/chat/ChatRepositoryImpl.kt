package ru.kraz.chat.data.chat

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.kraz.chat.domain.ResultApi
import ru.kraz.chat.domain.chat.ChatRepository
import ru.kraz.chat.domain.chat.MessageDomain

class ChatRepositoryImpl(
    private val dataSource: ChatCloudDataSource,
    private val firebaseAuth: FirebaseAuth,
) : ChatRepository {
    override suspend fun sendMessage(message: String) {
        dataSource.sendMessage(message)
    }

    override suspend fun sendMessageWithImage(message: String, filename: String, imgUri: String) {
        dataSource.sendMessageWithImage(message, filename, imgUri)
    }

    override suspend fun fetchMessage(): Flow<ResultApi<List<MessageDomain>>> = flow {
        try {
            dataSource.fetchMessage().collect { result ->
                val id = firebaseAuth.currentUser?.uid!!
                emit(ResultApi.Success(result.map { it.map(id) }.map { it.map() }))
            }
        } catch (e: Exception) {
            emit(ResultApi.Error(e.message ?: error))
        }
    }
}