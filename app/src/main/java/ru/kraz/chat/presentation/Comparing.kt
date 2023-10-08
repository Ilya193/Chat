package ru.kraz.chat.presentation

interface Comparing<T> {
    fun same(item: T): Boolean
    fun sameContent(item: T): Boolean
}