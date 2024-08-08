package com.alberto.calorietracker.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alberto.calorietracker.onboarding.presentation.FitnessOnboardingEvent
import com.alberto.calorietracker.onboarding.presentation.FitnessOnboardingPage
import com.alberto.calorietracker.onboarding.presentation.FitnessOnboardingState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
//import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FitnessOnboardingPager(
    pages: List<FitnessOnboardingPage>,
    onFinish: () -> Unit,
    modifier: Modifier = Modifier,
    onEvent: (FitnessOnboardingEvent) -> Unit
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    // Efecto para detectar cuando se llega a la última página
    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage == pages.size - 1) {
            onEvent(FitnessOnboardingEvent.CalculateDailyCalories)
        }
    }

    Column(modifier = modifier.fillMaxSize().background(Color.Black).systemBarsPadding()) {
        HorizontalPager(
            count = pages.size,
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(top = 85.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = pages[page].title,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = pages[page].subtitle,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(1.dp))
                pages[page].content()
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (pagerState.currentPage > 0) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                ) {
                    Text("Anterior")
                }
            }

            if (pagerState.currentPage < pages.size - 1) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                ) {
                    Text("Siguiente")
                }
            }
        }
    }
}