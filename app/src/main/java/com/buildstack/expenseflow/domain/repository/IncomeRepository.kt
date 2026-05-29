package com.buildstack.expenseflow.domain.repository

import com.buildstack.expenseflow.domain.model.Income
import kotlinx.coroutines.flow.Flow

interface IncomeRepository {
    fun getAllIncomes(): Flow<List<Income>>
    fun getTotalIncome(): Flow<Double>
    suspend fun insertIncome(income: Income)
    suspend fun deleteIncome(income: Income)
    suspend fun clearAllIncome()
}
