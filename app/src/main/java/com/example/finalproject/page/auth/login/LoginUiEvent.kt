package com.example.finalproject.page.auth.login

sealed interface LoginUiEvent {
    data class OnEmailChange(val email: String) : LoginUiEvent
    data class OnPasswordChanged(val password: String) : LoginUiEvent
    data class OnRememberLoginChanged(val checked: Boolean) : LoginUiEvent
    data object OnLoginClick : LoginUiEvent
    data object OnClearError : LoginUiEvent
}