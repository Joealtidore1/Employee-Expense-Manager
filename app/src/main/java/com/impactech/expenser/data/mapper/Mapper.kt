package com.impactech.expenser.data.mapper

import com.impactech.expenser.data.local.entity.EmployeeEntity
import com.impactech.expenser.data.local.entity.ExpenseEntity
import com.impactech.expenser.data.local.entity.MerchantEntity
import com.impactech.expenser.domain.model.Constant.userId
import com.impactech.expenser.domain.model.Employee
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
    receiptPath = receiptPath,
    userId = userId
)

fun Merchant.toMerchantEntity() = MerchantEntity(
    id = id,
    merchant = merchant
)

fun MerchantEntity.toMerchant() = Merchant(
    id = id,
    merchant = merchant
)

fun EmployeeEntity.toEmployee() = Employee(
    id = id,
    name = name,
    username = username,
    location = location,
    jobDescription = jobDescription,
    department = department
)

fun Employee.toEmployeeEntity() = EmployeeEntity(
    id = id,
    name = name,
    username = username,
    location = location,
    jobDescription = jobDescription,
    department = department,
    password = password!!
)