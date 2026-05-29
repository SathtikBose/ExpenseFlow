package com.buildstack.expenseflow.domain.usecase

import com.buildstack.expenseflow.domain.model.Expense
import com.buildstack.expenseflow.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpensesUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    operator fun invoke(): Flow<List<Expense>> {
        return repository.getAllExpenses()
    }
}
