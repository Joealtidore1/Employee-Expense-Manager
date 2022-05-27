package com.impactech.expenser.presentation.states_and_events

import com.impactech.expenser.domain.model.Employee

data class AuthState(
    val employee: Employee? = null,
    val username: String? = null,
    val password: String? = null,
)
