package com.buildstack.expenseflow.di

import com.buildstack.expenseflow.data.repository.DashboardRepositoryImpl
import com.buildstack.expenseflow.data.repository.ExpenseRepositoryImpl
import com.buildstack.expenseflow.data.repository.IncomeRepositoryImpl
import com.buildstack.expenseflow.domain.repository.DashboardRepository
import com.buildstack.expenseflow.domain.repository.ExpenseRepository
import com.buildstack.expenseflow.domain.repository.IncomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindIncomeRepository(
        incomeRepositoryImpl: IncomeRepositoryImpl
    ): IncomeRepository

    @Binds
    @Singleton
    abstract fun bindExpenseRepository(
        expenseRepositoryImpl: ExpenseRepositoryImpl
    ): ExpenseRepository

    @Binds
    @Singleton
    abstract fun bindDashboardRepository(
        dashboardRepositoryImpl: DashboardRepositoryImpl
    ): DashboardRepository

    @Binds
    @Singleton
    abstract fun bindGoalRepository(
        goalRepositoryImpl: com.buildstack.expenseflow.data.repository.GoalRepositoryImpl
    ): com.buildstack.expenseflow.domain.repository.GoalRepository
}
