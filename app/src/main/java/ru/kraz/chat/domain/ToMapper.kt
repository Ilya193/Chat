package ru.kraz.chat.domain

interface ToMapper<T> {
    fun map(): T
}