package com.example.finalproject.page.auth.register

sealed interface RegisterUiEvent {
    data class OnNameChanged(val name: String) : RegisterUiEvent
    data class OnEmailChanged(val email: String) : RegisterUiEvent
    data class OnPasswordChanged(val password: String) : RegisterUiEvent
    data class OnConfirmPasswordChanged(val confirmPassword: String) : RegisterUiEvent
    data object OnRegisterClick: RegisterUiEvent
    data object OnClearError: RegisterUiEvent
}