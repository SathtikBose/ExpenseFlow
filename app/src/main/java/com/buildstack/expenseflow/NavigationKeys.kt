package com.buildstack.expenseflow

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable data object Main : NavKey

@Serializable data object IncomeSetup : NavKey

@Serializable data class AddExpense(val expenseId: Int = -1) : NavKey

@Serializable data object Analytics : NavKey

@Serializable data object Goals : NavKey

