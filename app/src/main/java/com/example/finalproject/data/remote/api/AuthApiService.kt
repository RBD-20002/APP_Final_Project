package com.example.finalproject.data.remote.api

import com.example.finalproject.data.remote.dto.auth.UserCreateDTO
import com.example.finalproject.data.remote.dto.auth.UserLoginDTO
import com.example.finalproject.data.remote.dto.auth.UserResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("users/login")
    suspend fun login (@Body userLoginDTO: UserLoginDTO): Response<UserResponseDTO>

    @POST("users")
    suspend fun register (@Body request: UserCreateDTO): Response<UserResponseDTO>
}