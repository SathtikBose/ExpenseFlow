package com.buildstack.expenseflow.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun AnimatedCounter(
    count: Double,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    color: Color = style.color,
    fontWeight: FontWeight? = style.fontWeight,
    prefix: String = "",
    format: String = "%.2f"
) {
    val animatableCount = remember { Animatable(0f) }

    LaunchedEffect(count) {
        animatableCount.animateTo(
            targetValue = count.toFloat(),
            animationSpec = tween(durationMillis = 800)
        )
    }

    Row(modifier = modifier) {
        Text(
            text = "$prefix${String.format(format, animatableCount.value)}",
            style = style,
            color = color,
            fontWeight = fontWeight
        )
    }
}
