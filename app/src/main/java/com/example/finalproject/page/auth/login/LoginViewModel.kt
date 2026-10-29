package com.example.finalproject.page.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.finalproject.data.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEvent(event: LoginUiEvent){
        when (event) {
            is LoginUiEvent.OnEmailChange -> {
                _uiState.update { it.copy(email = event.email, errorMessage = null) }
            }
            is LoginUiEvent.OnPasswordChanged -> {
                _uiState.update { it.copy(password = event.password, errorMessage = null) }
            }
            is LoginUiEvent.OnRememberLoginChanged -> {
                _uiState.update { it.copy(rememberLogin = event.checked) }
            }
            is LoginUiEvent.OnClearError -> {
                _uiState.update { it.copy(errorMessage = null) }
            }
            LoginUiEvent.OnLoginClick -> {
                performLogin()
            }
        }
    }

    private fun performLogin() {
        val email = _uiState.value.email.trim()
        val password = _uiState.value.password

        if (email.isBlank() || password.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Por favor, completa todos los campos.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = authRepository.login(email, password)

            result.onSuccess { session ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = true,
                        userSession = session
                    )
                }
            }

            result.onFailure { e ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage ?: "Credenciales incorrectas o error de red"
                    )
                }
            }
        }
    }
}