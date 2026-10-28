package com.example.finalproject.data.remote.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginDTO (
    val userEmail: String,
    val userPassword: String
)