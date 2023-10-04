package ru.kraz.chat.domain

sealed class ResultApi<out T> {

    data class Success<T>(
        val data: T
    ): ResultApi<T>()

    data class Error(
        val e: String
    ): ResultApi<Nothing>()

}