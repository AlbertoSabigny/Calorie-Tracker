package com.alberto.calorietracker.core.presentation.mainscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alberto.calorietracker.home.presentation.diary.DiaryScreen
import com.alberto.calorietracker.profile.presentation.ProfileScreen
import com.alberto.calorietracker.recipes.presentation.RecipeScreen
import com.alberto.calorietracker.ui.theme.OrangeMedium
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate

@Composable
fun MainScreen(
    logout: () -> Unit,
    onNavigateToSearch: (LocalDate) -> Unit
    //onNavigateToDetail: (String) -> Unit
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.Black,
                //modifier = Modifier.height(72.dp)
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val items = listOf(
                    Triple("diary", Icons.Default.DateRange, "Diary"),
                    Triple("recipes", Icons.Default.ShoppingCart, "Recipes"),
                    Triple("profile", Icons.Default.Person, "Profile")
                )

                items.forEach { (route, icon, label) ->
                    val selected = currentRoute == route
                    BottomNavigationItem(
                        icon = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = if (selected) OrangeMedium else Color.White
                                )
                                //Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = label,
                                    color = if (selected) OrangeMedium else Color.White,
                                    fontSize = 12.sp
                                )
                            }
                        },
                        selected = selected,
                        onClick = { navController.navigate(route) },
                        selectedContentColor = OrangeMedium,
                        unselectedContentColor = Color.White
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "diary",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("diary") {
                DiaryScreen(
                    onNavigateToSearch = onNavigateToSearch
                    //onNavigateToDetail = onNavigateToDetail
                )
            }
            composable("profile") {
                ProfileScreen(logout = logout)
            }
            composable("recipes") {
                RecipeScreen()
            }
        }
    }
}

