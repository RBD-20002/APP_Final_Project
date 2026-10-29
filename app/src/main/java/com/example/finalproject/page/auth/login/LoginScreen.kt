package com.example.finalproject.page.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalproject.component.ActionButtons
import com.example.finalproject.component.CustomCheckBox
import com.example.finalproject.component.CustomInputField
import com.example.finalproject.component.PasswordInputField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.example.finalproject.R

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onEvent: (LoginUiEvent) -> Unit,
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onLoginSuccess()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.login_title),
                fontSize = 36.sp,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(32.dp))

            CustomInputField(
                value = uiState.email,
                onValueChange = { onEvent(LoginUiEvent.OnEmailChange(it))},
                label = stringResource(id = R.string.login_email_label),
                placeholder = stringResource(id = R.string.login_email_placeholder),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordInputField(
                value = uiState.password,
                onValueChange = { onEvent(LoginUiEvent.OnPasswordChanged(it)) },
                label = stringResource(id = R.string.login_password_label),
                placeholder = stringResource(id = R.string.login_password_placeholder),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            CustomCheckBox(
                checked = uiState.rememberLogin,
                onCheckedChange = { onEvent(LoginUiEvent.OnRememberLoginChanged(it)) },
                label = stringResource(id = R.string.login_remember_me)
            )

            if(uiState.errorMessage != null){
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = uiState.errorMessage,
                    fontSize = 13.sp,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            ActionButtons(
                cancelText = stringResource(id = R.string.login_btn_register),
                submitText = stringResource(id = R.string.login_btn_enter),
                onCancel = onNavigateToRegister,
                onSubmit = { onEvent(LoginUiEvent.OnLoginClick) },
                isSubmitEnabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth()
            )
        }
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.Black
            )
        }
    }
}