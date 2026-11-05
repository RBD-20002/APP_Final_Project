package com.example.finalproject.navigation

import kotlinx.serialization.Serializable

sealed interface NavRoute {

    @Serializable
    data object Login: NavRoute

    @Serializable
    data object Register: NavRoute

    @Serializable
    data object Category: NavRoute

    @Serializable
    data class ProductList(
        val idCategory: String,
        val catName: String = ""
    ): NavRoute

    @Serializable
    data class ProductDetail(
        val idProduct: String
    ): NavRoute

    @Serializable
    data class ProductForm(
        val idCategory: String,
        val idProduct: String ?= null
    ): NavRoute
}