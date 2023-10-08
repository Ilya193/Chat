package ru.kraz.chat.domain.chat

import kotlinx.coroutines.flow.Flow
import ru.kraz.chat.domain.ResultApi

class FetchMessageUseCase(
    private val repository: ChatRepository,
) {
    suspend operator fun invoke(): Flow<ResultApi<List<MessageDomain>>> {
        return repository.fetchMessage()
    }
}