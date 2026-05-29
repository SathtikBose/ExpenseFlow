package com.buildstack.expenseflow.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.buildstack.expenseflow.data.local.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expenses WHERE id = :id")
    suspend fun getExpenseById(id: Int): ExpenseEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity): Long

    @Query("DELETE FROM expenses")
    suspend fun clearAllExpenses(): Int

    @Delete
    suspend fun deleteExpense(expense: ExpenseEntity): Int

    @androidx.room.Update
    suspend fun updateExpense(expense: ExpenseEntity): Int
}
