package com.buildstack.expenseflow.di

import android.app.Application
import androidx.room.Room
import com.buildstack.expenseflow.data.local.AppDatabase
import com.buildstack.expenseflow.data.local.dao.ExpenseDao
import com.buildstack.expenseflow.data.local.dao.IncomeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "expenseflow_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideIncomeDao(db: AppDatabase): IncomeDao {
        return db.incomeDao
    }

    @Provides
    @Singleton
    fun provideExpenseDao(db: AppDatabase): ExpenseDao {
        return db.expenseDao
    }
}
