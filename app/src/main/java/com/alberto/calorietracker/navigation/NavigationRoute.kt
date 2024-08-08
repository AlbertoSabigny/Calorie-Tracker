package com.alberto.calorietracker.navigation

sealed class NavigationRoute(val route:String){
    object Onboarding: NavigationRoute("onboarding")
    object Login: NavigationRoute("login")
    object Diary : NavigationRoute("diary")
    object Signup : NavigationRoute("signup")
}