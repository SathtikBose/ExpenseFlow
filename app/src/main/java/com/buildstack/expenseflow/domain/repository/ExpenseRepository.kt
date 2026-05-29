package com.buildstack.expenseflow.domain.repository

import com.buildstack.expenseflow.domain.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getAllExpenses(): Flow<List<Expense>>
    suspend fun insertExpense(expense: Expense)
    suspend fun deleteExpense(expense: Expense)
    suspend fun clearAllExpenses()
}
