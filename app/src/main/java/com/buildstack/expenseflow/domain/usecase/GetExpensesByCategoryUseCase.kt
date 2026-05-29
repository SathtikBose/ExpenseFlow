package com.buildstack.expenseflow.domain.usecase

import com.buildstack.expenseflow.domain.model.ExpenseCategory
import com.buildstack.expenseflow.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

data class CategoryExpense(
    val category: ExpenseCategory,
    val totalAmount: Double,
    val percentage: Float
)

class GetExpensesByCategoryUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    operator fun invoke(): Flow<List<CategoryExpense>> {
        return expenseRepository.getAllExpenses().map { expenses ->
            val totalExpense = expenses.sumOf { it.amount }
            if (totalExpense == 0.0) return@map emptyList()

            expenses.groupBy { it.category }
                .map { (category, categoryExpenses) ->
                    val categoryTotal = categoryExpenses.sumOf { it.amount }
                    CategoryExpense(
                        category = category,
                        totalAmount = categoryTotal,
                        percentage = (categoryTotal / totalExpense).toFloat()
                    )
                }
                .sortedByDescending { it.totalAmount }
        }
    }
}
