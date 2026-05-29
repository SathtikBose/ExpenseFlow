package com.buildstack.expenseflow.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.buildstack.expenseflow.data.local.dao.ExpenseDao
import com.buildstack.expenseflow.data.local.dao.IncomeDao
import com.buildstack.expenseflow.data.local.entity.ExpenseEntity
import com.buildstack.expenseflow.data.local.entity.IncomeEntity

import com.buildstack.expenseflow.data.local.dao.GoalDao
import com.buildstack.expenseflow.data.local.entity.GoalEntity

@Database(
    entities = [IncomeEntity::class, ExpenseEntity::class, GoalEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun incomeDao(): IncomeDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun goalDao(): GoalDao
}
