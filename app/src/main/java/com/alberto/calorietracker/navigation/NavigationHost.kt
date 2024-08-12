package com.alberto.calorietracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alberto.calorietracker.authentication.presentation.login.LoginScreen
import com.alberto.calorietracker.authentication.presentation.signup.SignupScreen
import com.alberto.calorietracker.core.presentation.mainscreen.MainScreen
import com.alberto.calorietracker.home.presentation.detail.DetailScreen
import com.alberto.calorietracker.home.presentation.search.SearchFoodScreen
import com.alberto.calorietracker.onboarding.presentation.OnboardingScreen
import java.time.LocalDate

@Composable
fun NavigationHost (
    navHostController: NavHostController,
    startDestination: NavigationRoute,
    logout: () -> Unit
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
                navHostController.navigate(NavigationRoute.Main.route)
            }, onSignUp = {
                navHostController.navigate(NavigationRoute.Signup.route)
            })
        }

        composable(NavigationRoute.Signup.route) {
            SignupScreen(onSignIn = {
                navHostController.navigate(NavigationRoute.Main.route) {
                    popUpTo(navHostController.graph.id) {
                        inclusive = true
                    }
                }
            }, onLogin = {
                navHostController.popBackStack()
            })
        }
        composable(NavigationRoute.Main.route) {
            MainScreen(
                logout = logout,
                onNavigateToSearch = { date ->
                    // Convertimos la fecha a Long para pasarla como argumento de navegación
                    val dateLong = date.toEpochDay()
                    navHostController.navigate("${NavigationRoute.Search.route}/$dateLong")
                }
            )
        }

        composable(
            route = "${NavigationRoute.Search.route}/{date}",
            arguments = listOf(navArgument("date") { type = NavType.LongType })
        ) { backStackEntry ->
            val dateLong = backStackEntry.arguments?.getLong("date") ?: 0L
            val date = LocalDate.ofEpochDay(dateLong)
            SearchFoodScreen(
                date = date,
                onBack = { navHostController.popBackStack() },
                onFoodSelected = { foodId ->
                    navHostController.navigate("${NavigationRoute.FoodDetail.route}/$foodId")
                }
            )
        }

        composable(
            route = "${NavigationRoute.FoodDetail.route}/{foodId}",
            arguments = listOf(navArgument("foodId") { type = NavType.StringType })
        ) { backStackEntry ->
            val foodId = backStackEntry.arguments?.getString("foodId") ?: return@composable
            DetailScreen(
                foodId = foodId,
                onBack = { navHostController.popBackStack() }
            )
        }

    }
}

