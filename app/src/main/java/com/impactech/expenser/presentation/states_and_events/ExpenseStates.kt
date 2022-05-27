package com.impactech.expenser.presentation.states_and_events

data class ExpenseStates(
    val minDate: String? = null,
    val maxDate: String? = null,
    val minTotal: Double? = null,
    val maxTotal: Double? = null,
    val merchant: String? = null,
    val new: Boolean = true,
    val inProgress: Boolean = true,
    val reimbursed: Boolean = true
)