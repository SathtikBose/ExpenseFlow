package com.buildstack.expenseflow.presentation.goals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buildstack.expenseflow.domain.model.Goal
import com.buildstack.expenseflow.domain.usecase.AddGoalUseCase
import com.buildstack.expenseflow.domain.usecase.DeleteGoalUseCase
import com.buildstack.expenseflow.domain.usecase.GetGoalsUseCase
import com.buildstack.expenseflow.domain.usecase.UpdateGoalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalsViewModel @Inject constructor(
    getGoalsUseCase: GetGoalsUseCase,
    private val addGoalUseCase: AddGoalUseCase,
    private val updateGoalUseCase: UpdateGoalUseCase,
    private val deleteGoalUseCase: DeleteGoalUseCase
) : ViewModel() {

    val goals: StateFlow<List<Goal>> = getGoalsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addGoal(name: String, targetAmount: Double) {
        viewModelScope.launch {
            addGoalUseCase(Goal(name = name, targetAmount = targetAmount))
        }
    }

    fun updateGoal(goal: Goal) {
        viewModelScope.launch {
            updateGoalUseCase(goal)
        }
    }

    fun deleteGoal(goal: Goal) {
        viewModelScope.launch {
            deleteGoalUseCase(goal)
        }
    }
}
