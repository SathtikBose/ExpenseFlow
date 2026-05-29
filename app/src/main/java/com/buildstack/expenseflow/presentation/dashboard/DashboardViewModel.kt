package com.buildstack.expenseflow.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buildstack.expenseflow.domain.model.DashboardData
import com.buildstack.expenseflow.domain.usecase.GetDashboardDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import com.buildstack.expenseflow.domain.usecase.DeleteExpenseUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    getDashboardDataUseCase: GetDashboardDataUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase
) : ViewModel() {

    val dashboardData: StateFlow<DashboardData> = getDashboardDataUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DashboardData()
        )

    fun deleteExpense(expense: com.buildstack.expenseflow.domain.model.Expense) {
        viewModelScope.launch {
            deleteExpenseUseCase(expense)
        }
    }
}
