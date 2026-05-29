package com.buildstack.expenseflow.presentation.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buildstack.expenseflow.domain.model.Expense
import com.buildstack.expenseflow.domain.model.ExpenseCategory
import com.buildstack.expenseflow.domain.usecase.AddExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.buildstack.expenseflow.domain.usecase.GetExpenseByIdUseCase
import com.buildstack.expenseflow.domain.usecase.UpdateExpenseUseCase

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val addExpenseUseCase: AddExpenseUseCase,
    private val updateExpenseUseCase: UpdateExpenseUseCase,
    private val getExpenseByIdUseCase: GetExpenseByIdUseCase
) : ViewModel() {

    private var currentExpenseId: Int? = null

    private val _amount = MutableStateFlow("")
    val amount: StateFlow<String> = _amount.asStateFlow()

    private val _selectedCategory = MutableStateFlow(ExpenseCategory.FOOD)
    val selectedCategory: StateFlow<ExpenseCategory> = _selectedCategory.asStateFlow()

    private val _note = MutableStateFlow("")
    val note: StateFlow<String> = _note.asStateFlow()

    fun loadExpense(id: Int) {
        if (id == -1) return
        currentExpenseId = id
        viewModelScope.launch {
            val expense = getExpenseByIdUseCase(id)
            if (expense != null) {
                _amount.value = expense.amount.toString()
                _selectedCategory.value = expense.category
                _note.value = expense.note
            }
        }
    }

    fun onAmountChange(newAmount: String) {
        if (newAmount.isEmpty() || newAmount.matches(Regex("^\\d*\\.?\\d*$"))) {
            _amount.value = newAmount
        }
    }

    fun onCategorySelect(category: ExpenseCategory) {
        _selectedCategory.value = category
    }

    fun onNoteChange(newNote: String) {
        _note.value = newNote
    }

    fun saveExpense(onSuccess: () -> Unit) {
        val expenseAmount = _amount.value.toDoubleOrNull()
        if (expenseAmount != null && expenseAmount > 0) {
            viewModelScope.launch {
                val expense = Expense(
                    id = currentExpenseId ?: 0,
                    amount = expenseAmount,
                    category = _selectedCategory.value,
                    note = _note.value
                )
                if (currentExpenseId != null) {
                    updateExpenseUseCase(expense)
                } else {
                    addExpenseUseCase(expense)
                }
                onSuccess()
            }
        }
    }
}
