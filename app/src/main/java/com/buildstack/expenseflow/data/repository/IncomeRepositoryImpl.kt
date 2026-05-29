package com.buildstack.expenseflow.data.repository

import com.buildstack.expenseflow.data.local.dao.IncomeDao
import com.buildstack.expenseflow.data.local.entity.toDomain
import com.buildstack.expenseflow.data.local.entity.toEntity
import com.buildstack.expenseflow.domain.model.Income
import com.buildstack.expenseflow.domain.repository.IncomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class IncomeRepositoryImpl @Inject constructor(
    private val dao: IncomeDao
) : IncomeRepository {

    override fun getAllIncomes(): Flow<List<Income>> {
        return dao.getAllIncomes().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getTotalIncome(): Flow<Double> {
        return dao.getTotalIncome().map { it ?: 0.0 }
    }

    override suspend fun insertIncome(income: Income) {
        withContext(Dispatchers.IO) {
            dao.insertIncome(income.toEntity())
        }
    }

    override suspend fun deleteIncome(income: Income) {
        withContext(Dispatchers.IO) {
            dao.deleteIncome(income.toEntity())
        }
    }
}
