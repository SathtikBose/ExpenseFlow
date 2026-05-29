package com.buildstack.expenseflow.domain.usecase

import com.buildstack.expenseflow.domain.model.Goal
import com.buildstack.expenseflow.domain.repository.GoalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGoalsUseCase @Inject constructor(
    private val repository: GoalRepository
) {
    operator fun invoke(): Flow<List<Goal>> {
        return repository.getAllGoals()
    }
}
