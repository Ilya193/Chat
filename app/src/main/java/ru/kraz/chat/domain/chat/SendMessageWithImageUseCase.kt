package ru.kraz.chat.domain.chat

class SendMessageWithImageUseCase(
    private val repository: ChatRepository,
) {
    suspend operator fun invoke(message: String, filename: String, imgUri: String) {
        repository.sendMessageWithImage(message, filename, imgUri)
    }
}