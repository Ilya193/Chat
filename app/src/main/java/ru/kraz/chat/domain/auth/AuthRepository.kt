package ru.kraz.chat.domain.auth

import ru.kraz.chat.domain.ResultApi

interface AuthRepository {
    val error: String
        get() = "Something went wrong"

    suspend fun signUp(nickname: String, email: String, password: String): ResultApi<*>
    suspend fun signIn(email: String, password: String): ResultApi<*>
}