package com.buildstack.expenseflow.domain.usecase

import com.buildstack.expenseflow.domain.model.DashboardData
import com.buildstack.expenseflow.domain.repository.DashboardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDashboardDataUseCase @Inject constructor(
    private val repository: DashboardRepository
) {
    operator fun invoke(): Flow<DashboardData> {
        return repository.getDashboardData()
    }
}
