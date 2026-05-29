package com.buildstack.expenseflow.domain.repository

import com.buildstack.expenseflow.domain.model.DashboardData
import kotlinx.coroutines.flow.Flow

interface DashboardRepository {
    fun getDashboardData(): Flow<DashboardData>
}
