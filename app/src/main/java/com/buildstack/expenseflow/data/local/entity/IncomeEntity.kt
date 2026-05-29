package com.buildstack.expenseflow.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.buildstack.expenseflow.domain.model.Income

@Entity(tableName = "income")
data class IncomeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Double,
    val source: String,
    val date: Long
)

fun IncomeEntity.toDomain(): Income {
    return Income(
        id = id,
        amount = amount,
        source = source,
        date = date
    )
}

fun Income.toEntity(): IncomeEntity {
    return IncomeEntity(
        id = id,
        amount = amount,
        source = source,
        date = date
    )
}
