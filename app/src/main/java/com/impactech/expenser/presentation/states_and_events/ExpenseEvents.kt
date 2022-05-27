package com.impactech.expenser.presentation.states_and_events

import com.impactech.expenser.domain.model.Expense

sealed class ExpenseEvents{
    object Filter : ExpenseEvents()
    data class AddExpense(val expense: Expense): ExpenseEvents()
    data class UpdateExpense(val expense: Expense): ExpenseEvents()
    object ClearFilter: ExpenseEvents()
}
