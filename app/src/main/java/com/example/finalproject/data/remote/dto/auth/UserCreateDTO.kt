package com.example.finalproject.data.remote.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class UserCreateDTO (
    val userName: String,
    val userEmail: String,
    val userPassword: String
)