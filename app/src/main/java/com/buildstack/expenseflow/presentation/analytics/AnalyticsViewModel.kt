package com.buildstack.expenseflow.presentation.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buildstack.expenseflow.domain.usecase.CategoryExpense
import com.buildstack.expenseflow.domain.usecase.GetExpensesByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AnalyticsState(
    val categoryExpenses: List<CategoryExpense> = emptyList(),
    val totalExpenses: Double = 0.0
)

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val getExpensesByCategoryUseCase: GetExpensesByCategoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AnalyticsState())
    val state: StateFlow<AnalyticsState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getExpensesByCategoryUseCase().collect { categoryExpenses ->
                val total = categoryExpenses.sumOf { it.totalAmount }
                _state.value = AnalyticsState(
                    categoryExpenses = categoryExpenses,
                    totalExpenses = total
                )
            }
        }
    }
}
