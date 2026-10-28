package com.example.finalproject.data.remote.dto.product

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ProductUpdateDTO (
    val idProduct: String,
    val proName: String,
    val proAmount: Int,
    val proPrice: Double,
    val proMarketName: String,
    val proStatus: String,
    val imageUrl: String ?= null,
    val idCategory: String,
    val idUser: String,
    val dynamicAttributes: Map<String, JsonElement> = emptyMap()
)