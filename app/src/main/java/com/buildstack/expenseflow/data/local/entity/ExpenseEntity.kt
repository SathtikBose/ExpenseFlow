package com.buildstack.expenseflow.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.buildstack.expenseflow.domain.model.Expense
import com.buildstack.expenseflow.domain.model.ExpenseCategory

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Double,
    val category: String,
    val note: String,
    val date: Long
)

fun ExpenseEntity.toDomain(): Expense {
    return Expense(
        id = id,
        amount = amount,
        category = ExpenseCategory.valueOf(category),
        note = note,
        date = date
    )
}

fun Expense.toEntity(): ExpenseEntity {
    return ExpenseEntity(
        id = id,
        amount = amount,
        category = category.name,
        note = note,
        date = date
    )
}
