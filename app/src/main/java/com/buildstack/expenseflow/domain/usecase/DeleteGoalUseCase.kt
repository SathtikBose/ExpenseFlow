package com.buildstack.expenseflow.domain.usecase

import com.buildstack.expenseflow.domain.model.Goal
import com.buildstack.expenseflow.domain.repository.GoalRepository
import javax.inject.Inject

class DeleteGoalUseCase @Inject constructor(
    private val repository: GoalRepository
) {
    suspend operator fun invoke(goal: Goal) {
        repository.deleteGoal(goal)
    }
}
