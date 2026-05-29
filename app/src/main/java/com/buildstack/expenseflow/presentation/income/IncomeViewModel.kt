package com.buildstack.expenseflow.presentation.income

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buildstack.expenseflow.domain.model.Income
import com.buildstack.expenseflow.domain.usecase.GetTotalIncomeUseCase
import com.buildstack.expenseflow.domain.usecase.SaveIncomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.buildstack.expenseflow.domain.usecase.ResetFinancialCycleUseCase

@HiltViewModel
class IncomeViewModel @Inject constructor(
    private val getTotalIncomeUseCase: GetTotalIncomeUseCase,
    private val saveIncomeUseCase: SaveIncomeUseCase,
    private val resetFinancialCycleUseCase: ResetFinancialCycleUseCase
) : ViewModel() {

    val totalIncome: StateFlow<Double> = getTotalIncomeUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )

    fun saveIncome(amount: Double) {
        viewModelScope.launch {
            saveIncomeUseCase(
                Income(
                    amount = amount,
                    source = "Salary" // Default for now
                )
            )
        }
    }

    fun resetFinancialCycle() {
        viewModelScope.launch {
            resetFinancialCycleUseCase()
        }
    }
}
