package com.buildstack.expenseflow.domain.repository

import com.buildstack.expenseflow.domain.model.Goal
import kotlinx.coroutines.flow.Flow

interface GoalRepository {
    fun getAllGoals(): Flow<List<Goal>>
    suspend fun getGoalById(id: Int): Goal?
    suspend fun insertGoal(goal: Goal)
    suspend fun updateGoal(goal: Goal)
    suspend fun deleteGoal(goal: Goal)
    suspend fun clearAllGoals()
}
