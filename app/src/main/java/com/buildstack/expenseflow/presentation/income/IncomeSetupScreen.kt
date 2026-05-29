package com.buildstack.expenseflow.presentation.income

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.buildstack.expenseflow.core.theme.AccentColor
import com.buildstack.expenseflow.core.theme.BackgroundColor
import com.buildstack.expenseflow.core.theme.PrimaryColor
import com.buildstack.expenseflow.core.theme.SurfaceColor
import com.buildstack.expenseflow.core.theme.TextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeSetupScreen(
    viewModel: IncomeViewModel = hiltViewModel(),
    onIncomeSaved: () -> Unit
) {
    val totalIncome by viewModel.totalIncome.collectAsState()
    var amountText by remember { mutableStateOf("") }
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        BackgroundColor,
                        BackgroundColor.copy(alpha = 0.9f),
                        SurfaceColor
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(animationSpec = tween(500)) + slideInVertically(
                animationSpec = tween(500),
                initialOffsetY = { 100 }
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .shadow(16.dp, RoundedCornerShape(24.dp))
                    .clip(RoundedCornerShape(24.dp))
                    .background(SurfaceColor.copy(alpha = 0.8f))
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    text = "Set Monthly Income",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextColor,
                    style = MaterialTheme.typography.headlineMedium
                )

                Text(
                    text = "Current: $${String.format("%.2f", totalIncome)}",
                    fontSize = 16.sp,
                    color = TextColor.copy(alpha = 0.7f)
                )

                OutlinedTextField(
                    value = amountText,
                    onValueChange = { amountText = it },
                    label = { Text("Amount") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryColor,
                        unfocusedBorderColor = PrimaryColor.copy(alpha = 0.5f),
                        focusedTextColor = TextColor,
                        unfocusedTextColor = TextColor,
                        focusedLabelColor = PrimaryColor,
                        unfocusedLabelColor = TextColor.copy(alpha = 0.7f),
                        cursorColor = AccentColor
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        amountText.toDoubleOrNull()?.let {
                            viewModel.saveIncome(it)
                            onIncomeSaved()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryColor
                    )
                ) {
                    Text(
                        text = "Continue",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
