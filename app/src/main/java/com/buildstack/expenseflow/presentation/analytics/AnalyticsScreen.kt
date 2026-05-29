package com.buildstack.expenseflow.presentation.analytics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.buildstack.expenseflow.core.theme.BackgroundColor
import com.buildstack.expenseflow.core.theme.DangerColor
import com.buildstack.expenseflow.core.theme.PrimaryColor
import com.buildstack.expenseflow.core.theme.SurfaceColor
import com.buildstack.expenseflow.core.theme.TextPrimary
import com.buildstack.expenseflow.domain.usecase.CategoryExpense
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AnalyticsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AnalyticsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = BackgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Text("←", fontSize = 24.sp, color = TextPrimary)
                }
                Text(
                    text = "Analytics",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Total Expenses
            Text(
                text = "Total Spent",
                fontSize = 16.sp,
                color = TextPrimary.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$${String.format("%.2f", state.totalExpenses)}",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = DangerColor
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Custom Donut Chart
            if (state.categoryExpenses.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    DonutChart(
                        categoryExpenses = state.categoryExpenses,
                        modifier = Modifier.size(200.dp)
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No expenses yet.",
                        color = TextPrimary.copy(alpha = 0.5f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Spending by Category",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.categoryExpenses) { categoryExpense ->
                    CategoryProgressItem(categoryExpense)
                }
            }
        }
    }
}

@Composable
fun DonutChart(
    categoryExpenses: List<CategoryExpense>,
    modifier: Modifier = Modifier
) {
    // Generate colors for different categories. In a real app, bind these to the Category enum.
    val colors = listOf(
        PrimaryColor,
        DangerColor,
        Color(0xFF00D4FF), // SecondaryColor from previous iteration
        Color(0xFFFFB300),
        Color(0xFF00C853)
    )

    Canvas(modifier = modifier) {
        var startAngle = -90f
        val strokeWidth = 32.dp.toPx()

        for ((index, item) in categoryExpenses.withIndex()) {
            val sweepAngle = item.percentage * 360f
            drawArc(
                color = colors[index % colors.size],
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
            startAngle += sweepAngle
        }
    }
}

@Composable
fun CategoryProgressItem(item: CategoryExpense) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.category.displayName,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Text(
                text = "$${String.format("%.2f", item.totalAmount)}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        
        // Progress Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(CircleShape)
                .background(SurfaceColor)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = item.percentage.coerceIn(0f, 1f))
                    .height(8.dp)
                    .clip(CircleShape)
                    .background(PrimaryColor)
            )
        }
    }
}
