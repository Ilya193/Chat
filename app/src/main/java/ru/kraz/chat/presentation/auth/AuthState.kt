package ru.kraz.chat.presentation.auth

sealed class AuthState {

    data class Success(val nickname: String = "") : AuthState()
    data object Loading : AuthState()

    data class Error(val msg: String) : AuthState()
}