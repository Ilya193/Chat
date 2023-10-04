package ru.kraz.chat.domain.auth.sign_up

import ru.kraz.chat.domain.ResultApi
import ru.kraz.chat.domain.auth.AuthRepository

class SignUpUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(nickname: String, email: String, password: String): ResultApi<*> {
        return repository.signUp(nickname, email, password)
    }
}