package com.buildstack.expenseflow.data.repository

import com.buildstack.expenseflow.data.local.dao.ExpenseDao
import com.buildstack.expenseflow.data.local.entity.toDomain
import com.buildstack.expenseflow.data.local.entity.toEntity
import com.buildstack.expenseflow.domain.model.Expense
import com.buildstack.expenseflow.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val dao: ExpenseDao
) : ExpenseRepository {

    override fun getAllExpenses(): Flow<List<Expense>> {
        return dao.getAllExpenses().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun insertExpense(expense: Expense) {
        withContext(Dispatchers.IO) {
            dao.insertExpense(expense.toEntity())
        }
    }

    override suspend fun clearAllExpenses() {
        withContext(Dispatchers.IO) {
            dao.clearAllExpenses()
        }
    }

    override suspend fun deleteExpense(expense: Expense) {
        withContext(Dispatchers.IO) {
            dao.deleteExpense(expense.toEntity())
        }
    }
}
