package com.alberto.calorietracker.navigation

sealed class NavigationRoute(val route:String){
    object Onboarding: NavigationRoute("onboarding")
    object Login: NavigationRoute("login")
    object Diary : NavigationRoute("diary")//eliminar proximamente
    object Signup : NavigationRoute("signup")
    object Main : NavigationRoute("main")
    object Search : NavigationRoute("search")
    object Detail : NavigationRoute("detail")
}