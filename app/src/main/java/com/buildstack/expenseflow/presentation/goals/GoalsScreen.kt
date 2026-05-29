package com.buildstack.expenseflow.presentation.goals

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.buildstack.expenseflow.core.theme.*
import com.buildstack.expenseflow.domain.model.Goal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalsScreen(
    viewModel: GoalsViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val goals by viewModel.goals.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf<Goal?>(null) }
    var newGoalName by remember { mutableStateOf("") }
    var newGoalTarget by remember { mutableStateOf("") }

    Scaffold(
        containerColor = BackgroundColor,
        topBar = {
            TopAppBar(
                title = { Text("Financial Goals", color = TextPrimary) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("←", color = TextPrimary, fontSize = 24.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundColor)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = PrimaryColor,
                contentColor = Color.White
            ) {
                Text("+", fontSize = 24.sp)
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { Spacer(modifier = Modifier.height(8.dp)) }
                items(goals, key = { it.id }) { goal ->
                    GoalCard(
                        goal = goal,
                        onClick = { showEditDialog = goal }
                    )
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }

    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add Goal", color = TextPrimary) },
            text = {
                Column {
                    OutlinedTextField(
                        value = newGoalName,
                        onValueChange = { newGoalName = it },
                        label = { Text("Goal Name") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryColor,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newGoalTarget,
                        onValueChange = { newGoalTarget = it },
                        label = { Text("Target Amount") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryColor,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        )
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val target = newGoalTarget.toDoubleOrNull()
                    if (newGoalName.isNotBlank() && target != null) {
                        viewModel.addGoal(newGoalName, target)
                        newGoalName = ""
                        newGoalTarget = ""
                        showAddDialog = false
                    }
                }) {
                    Text("Save", color = PrimaryColor)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel", color = TextPrimary)
                }
            },
            containerColor = SurfaceColor
        )
    }

    if (showEditDialog != null) {
        var editSavedAmount by remember { mutableStateOf(showEditDialog!!.savedAmount.toString()) }
        AlertDialog(
            onDismissRequest = { showEditDialog = null },
            title = { Text("Update Progress", color = TextPrimary) },
            text = {
                Column {
                    Text("Goal: ${showEditDialog!!.name}", color = TextPrimary)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editSavedAmount,
                        onValueChange = { editSavedAmount = it },
                        label = { Text("Amount Saved") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryColor,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        )
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val saved = editSavedAmount.toDoubleOrNull()
                    if (saved != null) {
                        viewModel.updateGoal(showEditDialog!!.copy(savedAmount = saved))
                        showEditDialog = null
                    }
                }) {
                    Text("Update", color = PrimaryColor)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    viewModel.deleteGoal(showEditDialog!!)
                    showEditDialog = null
                }) {
                    Text("Delete Goal", color = DangerColor)
                }
            },
            containerColor = SurfaceColor
        )
    }
}

@Composable
fun GoalCard(goal: Goal, onClick: () -> Unit) {
    val progress = if (goal.targetAmount > 0) (goal.savedAmount / goal.targetAmount).toFloat().coerceIn(0f, 1f) else 0f
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(SurfaceColor.copy(alpha = 0.8f))
            .clickable(onClick = onClick)
            .padding(20.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = goal.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "${(progress * 100).toInt()}%",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryColor
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = PrimaryColor,
                trackColor = BackgroundColor
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Saved: $${String.format("%.2f", goal.savedAmount)}",
                    fontSize = 14.sp,
                    color = TextPrimary.copy(alpha = 0.7f)
                )
                Text(
                    text = "Target: $${String.format("%.2f", goal.targetAmount)}",
                    fontSize = 14.sp,
                    color = TextPrimary.copy(alpha = 0.7f)
                )
            }
        }
    }
}
