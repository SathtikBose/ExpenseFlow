package com.buildstack.expenseflow.di

import com.buildstack.expenseflow.data.repository.ExpenseRepositoryImpl
import com.buildstack.expenseflow.data.repository.IncomeRepositoryImpl
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
}
