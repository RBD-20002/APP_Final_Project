package com.example.finalproject.data.repository.auth

import com.example.finalproject.model.UserSession
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentSession: Flow<UserSession?>

    suspend fun login(email: String, password: String): Result<UserSession>
    suspend fun register(name: String, email: String, password: String): Result<UserSession>
    suspend fun logout()
}