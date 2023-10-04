package ru.kraz.chat.data.auth

import ru.kraz.chat.domain.ResultApi

interface CloudDataSource {
    suspend fun signUp(nickname: String, email: String, password: String): ResultApi<*>
}