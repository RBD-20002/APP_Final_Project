package com.example.finalproject.data.repository.auth

import com.example.finalproject.data.local.datastrore.SessionDataStore
import com.example.finalproject.data.remote.api.AuthApiService
import com.example.finalproject.data.remote.dto.auth.UserCreateDTO
import com.example.finalproject.data.remote.dto.auth.UserLoginDTO
import com.example.finalproject.model.UserSession
import kotlinx.coroutines.flow.Flow
import java.io.IOException


class AuthRepositoryImpl (
    private val authApiService: AuthApiService,
    private val sessionDataStore: SessionDataStore
): AuthRepository {
    override val currentSession: Flow<UserSession?> = sessionDataStore.userSession

    override suspend fun login(email: String, password: String): Result<UserSession> {
        return try {
            val response = authApiService.login(UserLoginDTO(email, password))

            if (response.isSuccessful && response.body() != null) {
                val dto = response.body()!!
                val userSession = UserSession(idUser = dto.idUser, userName = dto.userName, userEmail = dto.userEmail)
                sessionDataStore.saveSession(userSession)
                Result.success(userSession)

            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Error en el login"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Error de red"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(name: String, email: String, password: String): Result<UserSession> {
        return try {
            val response = authApiService.register(UserCreateDTO(userName = name, userEmail = email, userPassword = password))

            if(response.isSuccessful && response.body() != null) {
                val dto = response.body()!!
                val userSession = UserSession(idUser = dto.idUser, userName = dto.userName, userEmail = dto.userEmail)
                sessionDataStore.saveSession(userSession)
                Result.success(userSession)

            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Error en el registro"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Error de red"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        sessionDataStore.clearSession()
    }
}