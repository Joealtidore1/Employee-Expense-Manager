package com.impactech.expenser.presentation.states_and_events

import com.impactech.expenser.domain.model.Employee

sealed class AuthEvent {
    data class Login(val username: String, val password: String) : AuthEvent()
    object Logout : AuthEvent()
    data class Register(val employee: Employee) : AuthEvent()
    object GetUserInfo : AuthEvent()
}