package com.buildstack.expenseflow.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.buildstack.expenseflow.domain.model.Goal

@Entity(tableName = "goals")
data class GoalEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val targetAmount: Double,
    val savedAmount: Double,
    val deadline: Long?
)

fun GoalEntity.toDomain(): Goal {
    return Goal(
        id = id,
        name = name,
        targetAmount = targetAmount,
        savedAmount = savedAmount,
        deadline = deadline
    )
}

fun Goal.toEntity(): GoalEntity {
    return GoalEntity(
        id = id,
        name = name,
        targetAmount = targetAmount,
        savedAmount = savedAmount,
        deadline = deadline
    )
}
