package com.eeszen.locationtracking.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eeszen.locationtracking.ui.screens.home.HomeScreen
import com.eeszen.locationtracking.ui.screens.login.LoginScreen
import com.eeszen.locationtracking.ui.screens.register.RegisterScreen

@Composable
fun AppNav(){
    val navController = rememberNavController()
    NavHost(
        startDestination = Screen.Login,
        navController = navController
    ) {
        composable<Screen.Home> {
            HomeScreen()
        }
        composable<Screen.Login> {
            LoginScreen(navController = navController)
        }
        composable<Screen.Register> {
            RegisterScreen(navController = navController)
        }
    }
}