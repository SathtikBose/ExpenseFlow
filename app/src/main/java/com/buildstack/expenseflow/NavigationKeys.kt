package com.buildstack.expenseflow

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable data object Main : NavKey

@Serializable data object IncomeSetup : NavKey

@Serializable data object AddExpense : NavKey

@Serializable data object Analytics : NavKey

