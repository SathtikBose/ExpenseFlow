package com.buildstack.expenseflow.domain.model

data class Expense(
    val id: Int = 0,
    val amount: Double,
    val category: ExpenseCategory,
    val note: String,
    val date: Long = System.currentTimeMillis()
)
