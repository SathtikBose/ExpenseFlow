package com.buildstack.expenseflow.domain.model

enum class ExpenseCategory(val displayName: String, val iconResId: Int? = null) {
    FOOD("Food"),
    TRANSPORT("Transport"),
    BILLS("Bills"),
    ENTERTAINMENT("Entertainment"),
    SHOPPING("Shopping"),
    OTHER("Other")
}
