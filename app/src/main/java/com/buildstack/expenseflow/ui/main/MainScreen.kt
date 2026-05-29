package com.buildstack.expenseflow.ui.main

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.NavKey
import com.buildstack.expenseflow.AddExpense
import com.buildstack.expenseflow.IncomeSetup
import com.buildstack.expenseflow.core.theme.DangerColor
import com.buildstack.expenseflow.core.theme.BackgroundColor
import com.buildstack.expenseflow.core.theme.PrimaryColor
import com.buildstack.expenseflow.core.theme.SurfaceColor
import com.buildstack.expenseflow.core.theme.TextPrimary
import com.buildstack.expenseflow.presentation.dashboard.DashboardViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MainScreen(
    onItemClick: (NavKey) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val dashboardData by viewModel.dashboardData.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = BackgroundColor,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onItemClick(AddExpense) },
                containerColor = PrimaryColor,
                contentColor = Color.White
            ) {
                Text("+", fontSize = 24.sp)
            }
        }
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Overview",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                IconButton(onClick = { onItemClick(IncomeSetup) }) {
                    Text("⚙", fontSize = 24.sp, color = TextPrimary.copy(alpha = 0.7f))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Balance Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                SurfaceColor,
                                SurfaceColor.copy(alpha = 0.8f)
                            )
                        )
                    )
                    .padding(24.dp)
            ) {
                Column {
                    Text(
                        text = "Total Balance",
                        fontSize = 16.sp,
                        color = TextPrimary.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$${String.format("%.2f", dashboardData.balance)}",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Income",
                                fontSize = 14.sp,
                                color = TextPrimary.copy(alpha = 0.6f)
                            )
                            Text(
                                text = "+$${String.format("%.2f", dashboardData.totalIncome)}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = PrimaryColor
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "Expenses",
                                fontSize = 14.sp,
                                color = TextPrimary.copy(alpha = 0.6f)
                            )
                            Text(
                                text = "-$${String.format("%.2f", dashboardData.totalExpenses)}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = DangerColor
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Recent Transactions",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(dashboardData.recentExpenses) { expense ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(SurfaceColor.copy(alpha = 0.5f))
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = expense.category.displayName,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )
                            if (expense.note.isNotEmpty()) {
                                Text(
                                    text = expense.note,
                                    fontSize = 14.sp,
                                    color = TextPrimary.copy(alpha = 0.6f)
                                )
                            }
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "-$${String.format("%.2f", expense.amount)}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            val formatter = SimpleDateFormat("MMM dd", Locale.getDefault())
                            Text(
                                text = formatter.format(Date(expense.date)),
                                fontSize = 12.sp,
                                color = TextPrimary.copy(alpha = 0.5f)
                            )
                        }
                    }
                }
            }
        }
    }
}
