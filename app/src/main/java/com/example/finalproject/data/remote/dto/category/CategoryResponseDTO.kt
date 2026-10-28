package com.example.finalproject.data.remote.dto.category

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponseDTO (
    val idCategory: String,
    val catName: String,
    val catIconKey: String,
    val idUser: String
)