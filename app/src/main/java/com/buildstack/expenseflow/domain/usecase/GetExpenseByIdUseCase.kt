package com.buildstack.expenseflow.domain.usecase

import com.buildstack.expenseflow.domain.model.Expense
import com.buildstack.expenseflow.domain.repository.ExpenseRepository
import javax.inject.Inject

class GetExpenseByIdUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    suspend operator fun invoke(id: Int): Expense? {
        return repository.getExpenseById(id)
    }
}
