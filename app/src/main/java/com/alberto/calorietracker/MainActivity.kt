package com.alberto.calorietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.alberto.calorietracker.home.presentation.diary.DiaryScreen
import com.alberto.calorietracker.navigation.NavigationHost
import com.alberto.calorietracker.navigation.NavigationRoute
import com.alberto.calorietracker.onboarding.presentation.OnboardingScreen
//import com.alberto.calorietracker.home.diary.diaryScreen
import com.alberto.calorietracker.ui.theme.CalorieTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    private val viewModel by viewModels<MainViewModel> ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // enableEdgeToEdge()
        setContent {
            CalorieTrackerTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavigationHost(
                        navHostController = navController,
                        startDestination = getStartDestination(),
                        logout = {
                            viewModel.logout()
                        }

                    )
                }
            }
        }
    }
    private fun getStartDestination(): NavigationRoute {
        if (viewModel.isLoggedIn) {
            return NavigationRoute.Main
        }
        return if (viewModel.hasSeenOnboarding) {
            NavigationRoute.Login
        } else {
            NavigationRoute.Onboarding
        }
    }

}

