package ru.kraz.chat.presentation.auth

sealed class AuthState {

    data object Success : AuthState()
    data object Loading : AuthState()

    data class Error(val msg: String) : AuthState()
}