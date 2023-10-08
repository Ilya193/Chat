package ru.kraz.chat.domain

interface ToUiMapper<T, R> {
    fun map(data: T): R
}