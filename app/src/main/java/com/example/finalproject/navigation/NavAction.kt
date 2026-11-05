package com.example.finalproject.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

class NavAction(private val navController: NavHostController) {

    fun up(): Boolean = navController.navigateUp()

    fun toLogin() {
        navController.navigate(NavRoute.Login) {
            popUpTo(0) { inclusive = true }
            launchSingleTop = true
        }
    }

    fun toRegister() {
        navController.navigate(NavRoute.Register) {
            launchSingleTop = true
        }
    }

    fun toCategory() {
        navController.navigate(NavRoute.Category) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun toProductList(idCategory: String, catName: String) {
        navController.navigate(NavRoute.ProductList(idCategory = idCategory, catName = catName))
    }

    fun toProductDetail(idProduct: String) {
        navController.navigate(NavRoute.ProductDetail(idProduct = idProduct))
    }

    fun toProductForm(idCategory: String, idProduct: String ?= null) {
        navController.navigate(NavRoute.ProductForm(idCategory = idCategory, idProduct = idProduct))
    }
}