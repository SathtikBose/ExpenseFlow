package com.buildstack.expenseflow.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import com.buildstack.expenseflow.AddExpense
import com.buildstack.expenseflow.IncomeSetup

@Composable
fun MainScreen(
    onItemClick: (NavKey) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Dashboard (Coming Soon)")
            Button(onClick = { onItemClick(IncomeSetup) }) {
                Text("Setup Income")
            }
            Button(onClick = { onItemClick(AddExpense) }) {
                Text("Add Expense")
            }
        }
    }
}
