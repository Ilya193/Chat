package ru.kraz.chat.domain.auth

import ru.kraz.chat.domain.ResultApi

class SignUpUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(nickname: String, email: String, password: String): ResultApi<*> {
        return repository.signUp(nickname, email, password)
    }
}