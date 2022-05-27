package com.impactech.expenser.domain


import com.impactech.expenser.domain.model.Expense
import com.impactech.expenser.domain.model.Merchant

interface ExpenseInterface {

        suspend fun getAllMerchant(): List<String>

        suspend fun addMerchants(merchant: List<Merchant>)


        suspend fun addExpense(expense: Expense)

        suspend fun updateExpense(expense: Expense)

        suspend fun getAllExpenses(): List<Expense>

        suspend fun getExpensesByFilter(
                status: String? = "",
                merchant: String?,
                minTotal: Double?,
                maxTotal: Double?,
                minDate: String?,
                maxDate: String?
        ): List<Expense>

        suspend fun getExpensesByNotFilter(
                status: String,
                merchant: String?,
                minTotal: Double?,
                maxTotal: Double?,
                minDate: String?,
                maxDate: String?
        ): List<Expense>
}