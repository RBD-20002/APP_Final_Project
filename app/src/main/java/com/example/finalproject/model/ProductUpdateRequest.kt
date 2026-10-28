package com.example.finalproject.model

data class ProductUpdateRequest (
    val idProduct: String,
    val proName: String,
    val proAmount: Int,
    val proPrice: Double,
    val proMarketName: String,
    val proStatus: String ?= null,
    val imageUrl: String ?= null,
    val idCategory: String,
    val idUser: String,
    val dynamicAttributes: Map<String, Any> = emptyMap()
)