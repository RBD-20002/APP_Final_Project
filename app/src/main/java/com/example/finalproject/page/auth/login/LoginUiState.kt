package com.example.finalproject.page.auth.login

import com.example.finalproject.model.UserSession

data class LoginUiState (
    val email: String = "",
    val password: String = "",
    val rememberLogin: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
    val userSession: UserSession ?= null
)