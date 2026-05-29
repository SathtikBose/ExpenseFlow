package com.buildstack.expenseflow.domain.model

data class Income(
    val id: Int = 0,
    val amount: Double,
    val source: String = "Salary",
    val date: Long = System.currentTimeMillis()
)
