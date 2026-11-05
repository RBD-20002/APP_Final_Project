package com.example.finalproject.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController

@Composable
fun rememberNavActions(navController: NavHostController): NavAction = remember(navController) {
    NavAction(navController)
}