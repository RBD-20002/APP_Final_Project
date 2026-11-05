package com.example.finalproject.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import com.example.finalproject.model.SessionState

@Composable
fun SessionNavEffect(
    navController: NavHostController,
    sessionState: SessionState
) {
    LaunchedEffect(sessionState) {
        val currentDestination = navController.currentDestination

        when (sessionState) {
            is SessionState.Loading -> Unit

            is SessionState.LoggedOut -> {
                val isAuthScreen = currentDestination?.hasRoute<NavRoute.Login>() == true ||
                        currentDestination?.hasRoute<NavRoute.Register>() == true

                if (!isAuthScreen) {
                    navController.navigate(NavRoute.Login) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }

            is SessionState.LoggedIn -> {
                val isAuthScreen = currentDestination?.hasRoute<NavRoute.Login>() == true ||
                        currentDestination?.hasRoute<NavRoute.Register>() == true

                if (isAuthScreen) {
                    navController.navigate(NavRoute.Category) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }
        }
    }
}