package com.example.finalproject.model

data class CategoryUpdateRequest (
    val idCategory: String,
    val catName: String,
    val catIconKey: String,
    val idUser: String
)