package com.buildstack.expenseflow.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.buildstack.expenseflow.data.local.entity.IncomeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IncomeDao {
    
    @Query("SELECT * FROM income ORDER BY date DESC")
    fun getAllIncomes(): Flow<List<IncomeEntity>>

    @Query("SELECT SUM(amount) FROM income")
    fun getTotalIncome(): Flow<Double?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIncome(income: IncomeEntity): Long

    @Query("DELETE FROM income")
    suspend fun clearAllIncome(): Int

    @Delete
    suspend fun deleteIncome(income: IncomeEntity): Int
}
