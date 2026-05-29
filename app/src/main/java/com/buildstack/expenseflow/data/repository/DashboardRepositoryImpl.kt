package com.buildstack.expenseflow.data.repository

import com.buildstack.expenseflow.data.local.dao.ExpenseDao
import com.buildstack.expenseflow.data.local.dao.IncomeDao
import com.buildstack.expenseflow.data.local.entity.toDomain
import com.buildstack.expenseflow.domain.model.DashboardData
import com.buildstack.expenseflow.domain.repository.DashboardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val incomeDao: IncomeDao,
    private val expenseDao: ExpenseDao
) : DashboardRepository {

    override fun getDashboardData(): Flow<DashboardData> {
        val totalIncomeFlow = incomeDao.getTotalIncome()
        val allExpensesFlow = expenseDao.getAllExpenses()

        return combine(totalIncomeFlow, allExpensesFlow) { totalIncome, expenses ->
            val income = totalIncome ?: 0.0
            val totalExpenses = expenses.sumOf { it.amount }
            val balance = income - totalExpenses
            
            DashboardData(
                totalIncome = income,
                totalExpenses = totalExpenses,
                balance = balance,
                recentExpenses = expenses.take(5).map { it.toDomain() }
            )
        }
    }
}
