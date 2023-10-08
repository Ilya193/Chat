package ru.kraz.chat.data.auth

import ru.kraz.chat.domain.ResultApi
import ru.kraz.chat.domain.auth.AuthRepository

class AuthRepositoryImpl(
    private val dataSource: AuthCloudDataSource,
) : AuthRepository {

    override suspend fun signUp(nickname: String, email: String, password: String): ResultApi<*> {
        return handleExceptions {
            dataSource.signUp(nickname, email, password)
        }
    }

    override suspend fun signIn(email: String, password: String): ResultApi<*> {
        return handleExceptions {
            dataSource.signIn(email, password)
        }
    }

    private suspend fun <T> handleExceptions(block: suspend () -> ResultApi<T>): ResultApi<T> {
        return try {
            block()
        } catch (e: Exception) {
            ResultApi.Error(e.message ?: error)
        }
    }
}