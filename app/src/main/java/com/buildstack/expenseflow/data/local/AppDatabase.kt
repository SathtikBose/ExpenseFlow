package com.buildstack.expenseflow.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.buildstack.expenseflow.data.local.dao.ExpenseDao
import com.buildstack.expenseflow.data.local.dao.IncomeDao
import com.buildstack.expenseflow.data.local.entity.ExpenseEntity
import com.buildstack.expenseflow.data.local.entity.IncomeEntity

@Database(
    entities = [IncomeEntity::class, ExpenseEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val incomeDao: IncomeDao
    abstract val expenseDao: ExpenseDao
}
