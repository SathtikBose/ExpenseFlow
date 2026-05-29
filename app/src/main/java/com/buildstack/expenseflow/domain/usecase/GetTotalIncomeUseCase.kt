package com.buildstack.expenseflow.domain.usecase

import com.buildstack.expenseflow.domain.repository.IncomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTotalIncomeUseCase @Inject constructor(
    private val repository: IncomeRepository
) {
    operator fun invoke(): Flow<Double> {
        return repository.getTotalIncome()
    }
}
