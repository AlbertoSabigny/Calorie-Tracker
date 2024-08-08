package com.alberto.calorietracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alberto.calorietracker.authentication.presentation.login.LoginScreen
import com.alberto.calorietracker.authentication.presentation.signup.SignupScreen
import com.alberto.calorietracker.home.diary.DiaryScreen
import com.alberto.calorietracker.onboarding.presentation.OnboardingScreen

@Composable
fun NavigationHost (
    navHostController: NavHostController,
    startDestination: NavigationRoute,

) {
    NavHost(navController = navHostController, startDestination = startDestination.route){
        composable(NavigationRoute.Onboarding.route){
            OnboardingScreen (
                onFinish = {
                    navHostController.popBackStack()
                    navHostController.navigate(NavigationRoute.Login.route)}
            )
        }
        composable(NavigationRoute.Login.route){
            LoginScreen(onLogin = {
                navHostController.popBackStack()
                navHostController.navigate(NavigationRoute.Diary.route)
            }, onSignUp = {
                navHostController.navigate(NavigationRoute.Signup.route)
            })
        }

        composable(NavigationRoute.Signup.route) {
            SignupScreen(onSignIn = {
                navHostController.navigate(NavigationRoute.Diary.route) {
                    popUpTo(navHostController.graph.id) {
                        inclusive = true
                    }
                }
            }, onLogin = {
                navHostController.popBackStack()
            })
        }

        composable(NavigationRoute.Diary.route) {
            DiaryScreen()
        }

    }
}