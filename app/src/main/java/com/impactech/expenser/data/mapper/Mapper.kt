package com.impactech.expenser.data.mapper

import com.impactech.expenser.data.local.ExpenseEntity
import com.impactech.expenser.data.local.MerchantEntity
import com.impactech.expenser.domain.model.Expense
import com.impactech.expenser.domain.model.Merchant

fun ExpenseEntity.toExpense() = Expense(
    id = id,
    total = total,
    date = date,
    merchant = merchant,
    comment = comment,
    status = status,
    receiptPath = receiptPath
)

fun Expense.toExpenseEntity() = ExpenseEntity(
    id = id,
    total = total,
    date = date,
    merchant = merchant,
    comment = comment,
    status = status,
    receiptPath = receiptPath
)

fun Merchant.toMerchantEntity() = MerchantEntity(
    id = id,
    merchant = merchant
)

fun MerchantEntity.toMerchant() = Merchant(
    id = id,
    merchant = merchant
)