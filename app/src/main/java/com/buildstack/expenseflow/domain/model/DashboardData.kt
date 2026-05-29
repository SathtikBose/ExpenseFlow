package com.buildstack.expenseflow.domain.model

data class DashboardData(
    val totalIncome: Double = 0.0,
    val totalExpenses: Double = 0.0,
    val balance: Double = 0.0,
    val recentExpenses: List<Expense> = emptyList()
)
