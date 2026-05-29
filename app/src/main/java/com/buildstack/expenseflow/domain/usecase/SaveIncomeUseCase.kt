package com.buildstack.expenseflow.domain.usecase

import com.buildstack.expenseflow.domain.model.Income
import com.buildstack.expenseflow.domain.repository.IncomeRepository
import javax.inject.Inject

class SaveIncomeUseCase @Inject constructor(
    private val repository: IncomeRepository
) {
    suspend operator fun invoke(income: Income) {
        repository.insertIncome(income)
    }
}
