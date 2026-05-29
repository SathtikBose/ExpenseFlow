package com.buildstack.expenseflow.domain.model

data class Goal(
    val id: Int = 0,
    val name: String,
    val targetAmount: Double,
    val savedAmount: Double = 0.0,
    val deadline: Long? = null
)
