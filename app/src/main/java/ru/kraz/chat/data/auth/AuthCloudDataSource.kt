package ru.kraz.chat.data.auth

import ru.kraz.chat.domain.ResultApi

interface AuthCloudDataSource {
    suspend fun signUp(nickname: String, email: String, password: String): ResultApi<*>
    suspend fun signIn(email: String, password: String): ResultApi<*>
}