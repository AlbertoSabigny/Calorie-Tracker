package com.alberto.calorietracker.home.diary.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alberto.calorietracker.R

@Composable
fun  MacroNutrientCard (
    carbs: Pair<Int, Int>,
    proteins: Pair<Int, Int>,
    fats: Pair<Int, Int>,
    calories: Pair<Int, Int>,
    modifier: Modifier = Modifier
){
    // Creamos un estado animado para el progreso
    val animatedProgress = remember { Animatable(0f) }

    // Lanzamos la animación cuando el composable entra en la composición
    LaunchedEffect(key1 = calories) {
        animatedProgress.animateTo(
            targetValue = calories.first.toFloat() / calories.second,
            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        )
    }

    Card (modifier= Modifier
        .fillMaxWidth()
        .padding(16.dp)){

        Column (
            modifier = Modifier.padding(16.dp)
        ){
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Nutrientnfo(
                    iconResId = R.drawable.carbs,
                    name = "Carbs",
                    value = carbs)
                Nutrientnfo(
                    iconResId = R.drawable.protein,
                    name = "Protein",
                    value = proteins)
                Nutrientnfo(
                    iconResId = R.drawable.fat,
                    name = "Fat",
                    value = fats)
            }
            Spacer(modifier = Modifier.height(16.dp))

            LinearProgressIndicator(
                progress = animatedProgress.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = Color(0xFFF57C00),  // Color de la barra de progreso (verde en este ejemplo)
                trackColor = Color(0xFFFFE0B2)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Calorías: ${calories.first}/${calories.second}",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 15.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

        }

    }
}
