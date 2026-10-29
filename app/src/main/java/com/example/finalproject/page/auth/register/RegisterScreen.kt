package com.example.finalproject.page.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalproject.R
import com.example.finalproject.component.ActionButtons
import com.example.finalproject.component.CustomInputField
import com.example.finalproject.component.PasswordInputField

@Composable
fun RegisterScreen(
    uiState: RegisterUiState,
    onEvent: (RegisterUiEvent) -> Unit,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onNavigateToLogin()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.register_title),
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 32.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            CustomInputField(
                value = uiState.name,
                onValueChange = { onEvent(RegisterUiEvent.OnNameChanged(it)) },
                label = stringResource(id = R.string.register_name_label),
                placeholder = stringResource(id = R.string.register_name_placeholder),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(14.dp))

            CustomInputField(
                value = uiState.email,
                onValueChange = { onEvent(RegisterUiEvent.OnEmailChanged(it)) },
                label = stringResource(id = R.string.register_email_label),
                placeholder = stringResource(id = R.string.register_email_placeholder),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(14.dp))

            PasswordInputField(
                value = uiState.password,
                onValueChange = { onEvent(RegisterUiEvent.OnPasswordChanged(it)) },
                label = stringResource(id = R.string.register_password_label),
                placeholder = stringResource(id = R.string.register_password_placeholder),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(14.dp))

            PasswordInputField(
                value = uiState.confirmPassword,
                onValueChange = { onEvent(RegisterUiEvent.OnConfirmPasswordChanged(it)) },
                label = stringResource(id = R.string.register_confirm_label),
                placeholder = stringResource(id = R.string.register_password_placeholder),
                modifier = Modifier.fillMaxWidth()
            )

            if (uiState.errorMessage != null) {
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = uiState.errorMessage,
                    fontSize = 14.sp,
                    color = Color.Red
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            ActionButtons(
                cancelText = stringResource(id = R.string.register_btn_back),
                submitText = stringResource(id = R.string.register_btn_submit),
                onCancel = onNavigateToLogin,
                onSubmit = { onEvent(RegisterUiEvent.OnRegisterClick) },
                isSubmitEnabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}