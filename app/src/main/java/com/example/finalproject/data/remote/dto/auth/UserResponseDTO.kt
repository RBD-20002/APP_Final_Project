package com.example.finalproject.data.remote.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class UserResponseDTO (
    val idUser: String,
    val userName: String,
    val userEmail: String
)