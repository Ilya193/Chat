package ru.kraz.chat.domain.auth.sign_in

import ru.kraz.chat.domain.ResultApi
import ru.kraz.chat.domain.auth.AuthRepository

class SignInUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): ResultApi<*> {
        return repository.signIn(email, password)
    }
}