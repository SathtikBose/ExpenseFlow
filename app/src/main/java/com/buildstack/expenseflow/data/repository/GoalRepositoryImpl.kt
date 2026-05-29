package com.buildstack.expenseflow.data.repository

import com.buildstack.expenseflow.data.local.dao.GoalDao
import com.buildstack.expenseflow.data.local.entity.toDomain
import com.buildstack.expenseflow.data.local.entity.toEntity
import com.buildstack.expenseflow.domain.model.Goal
import com.buildstack.expenseflow.domain.repository.GoalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GoalRepositoryImpl @Inject constructor(
    private val dao: GoalDao
) : GoalRepository {
    override fun getAllGoals(): Flow<List<Goal>> {
        return dao.getAllGoals().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getGoalById(id: Int): Goal? {
        return withContext(Dispatchers.IO) {
            dao.getGoalById(id)?.toDomain()
        }
    }

    override suspend fun insertGoal(goal: Goal) {
        withContext(Dispatchers.IO) {
            dao.insertGoal(goal.toEntity())
        }
    }

    override suspend fun updateGoal(goal: Goal) {
        withContext(Dispatchers.IO) {
            dao.updateGoal(goal.toEntity())
        }
    }

    override suspend fun deleteGoal(goal: Goal) {
        withContext(Dispatchers.IO) {
            dao.deleteGoal(goal.toEntity())
        }
    }

    override suspend fun clearAllGoals() {
        withContext(Dispatchers.IO) {
            dao.clearAllGoals()
        }
    }
}
