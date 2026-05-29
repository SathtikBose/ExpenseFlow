package com.buildstack.expenseflow.domain.usecase

import com.buildstack.expenseflow.domain.repository.ExpenseRepository
import com.buildstack.expenseflow.domain.repository.IncomeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class ResetFinancialCycleUseCase @Inject constructor(
    private val incomeRepository: IncomeRepository,
    private val expenseRepository: ExpenseRepository
) {
    suspend operator fun invoke() {
        coroutineScope {
            val incomeJob = async { incomeRepository.clearAllIncome() }
            val expenseJob = async { expenseRepository.clearAllExpenses() }
            incomeJob.await()
            expenseJob.await()
        }
    }
}
