package com.alberto.calorietracker.authentication.presentation.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alberto.calorietracker.core.domain.model.UserProfile
import com.alberto.calorietracker.R
import com.alberto.calorietracker.authentication.presentation.login.components.LoginForm

@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    onSignUp: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.state

    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) {
            onLogin()
        }
    }

    Box(modifier = Modifier.fillMaxSize().systemBarsPadding()) {
        Image(
            painter = painterResource(id = R.drawable.welcome),
            contentDescription = "background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.aspectRatio(1f).graphicsLayer(
                scaleX = 1.15f,
                scaleY = 1.15f
            )
        )
        Spacer(
            modifier = Modifier.fillMaxSize().background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Black.copy(alpha = 0.5f),
                        Color.Black.copy(alpha = 0.8f),
                        Color.Black
                    )
                )
            )
        )
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            Spacer(modifier = Modifier)
            Spacer(modifier = Modifier)

            LoginForm(state, viewModel::onEvent, onSignUp)
        }
    }
}
