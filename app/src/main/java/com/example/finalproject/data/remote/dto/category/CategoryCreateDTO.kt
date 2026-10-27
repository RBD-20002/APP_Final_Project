package com.example.finalproject.data.remote.dto.category

import kotlinx.serialization.Serializable

@Serializable
data class CategoryCreateDTO (
    val catName: String,
    val catIconKey: String,
    val idUser: String
)