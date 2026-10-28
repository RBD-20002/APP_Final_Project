package com.example.finalproject.data.remote.dto.category

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryUpdateDTO (
    val idCategory: String,
    val catName: String,

    @SerialName("catIconKey")
    val catIconKey: String,

    val idUser: String
)