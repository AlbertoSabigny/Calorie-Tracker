package com.alberto.calorietracker.home.presentation.diary.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import  androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Nutrientnfo (
    @DrawableRes iconResId: Int,
    name: String,
    value: Pair<Int, Int>
){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = name,
            modifier = Modifier.size(56.dp)
        )
        Text(text = name, style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp))
        Text(
            text = "${value.first}/${value.second}",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp)
        )
    }
}