package com.impactech.expenser.domain

import com.impactech.expenser.domain.model.Employee

interface ProfileRepository {
    suspend fun getEmployee(username: String = "demo", password: String = "demo"): Employee?

    suspend fun verifyEmployee(username: String): Employee?

    suspend fun addEmployee(employee: Employee)

    suspend fun initialize()
}