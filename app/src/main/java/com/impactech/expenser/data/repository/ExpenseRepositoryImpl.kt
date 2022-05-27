package com.impactech.expenser.data.repository

import com.impactech.expenser.data.local.ExpenseDatabase
import com.impactech.expenser.data.mapper.toExpense
import com.impactech.expenser.data.mapper.toExpenseEntity
import com.impactech.expenser.data.mapper.toMerchantEntity
import com.impactech.expenser.domain.ExpenseInterface
import com.impactech.expenser.domain.model.Expense
import com.impactech.expenser.domain.model.Merchant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseRepositoryImpl @Inject constructor(
    db: ExpenseDatabase
): ExpenseInterface {
    private val dao = db.expenseDao
    override suspend fun getReimbursementAmount(): Double? = dao.getTotalByStatus()

    override suspend fun getAllMerchant() = dao.getMerchants()

    override suspend fun addMerchants(merchant: List<Merchant>) {
        dao.addMerchant(merchant.map{it.toMerchantEntity()})
    }

    override suspend fun addExpense(expense: Expense) {
        dao.addExpense(expense.toExpenseEntity())
    }

    override suspend fun updateExpense(expense: Expense) {
        dao.updateExpense(expense.toExpenseEntity())
    }

    override suspend fun getAllExpenses() = dao.getExpenses("").map { it.toExpense() }

    override suspend fun getExpensesByFilter(
        status: String?,
        merchant: String?,
        minTotal: Double?,
        maxTotal: Double?,
        minDate: String?,
        maxDate: String?
    ): List<Expense> {
        val universalSet = dao.getExpenses(status?:"").toSet()
        val byTotalSet = if(minTotal == null && maxTotal == null) {
            universalSet
        }else {
            if(minTotal == null) {
                universalSet.intersect(dao.getExpensesByMax(maxTotal!!).toSet())
            }else if(maxTotal == null){
                universalSet.intersect(dao.getExpensesByMin(minTotal).toSet())
            }else{
                val total = dao.getExpensesByMax(maxTotal).toSet().intersect(
                    dao.getExpensesByMin(minTotal).toSet()
                )
                universalSet.intersect(total)
            }
        }
        val byDateSet = if(minDate == null && maxDate == null) {
            byTotalSet
        }else {
            if(minDate == null) {
                byTotalSet.intersect(dao.getExpensesByMaxDate(maxDate!!).toSet())
            }else if(maxDate == null){
                byTotalSet.intersect(dao.getExpensesByMinDate(minDate).toSet())
            }else{
                val total = dao.getExpensesByMaxDate(maxDate).intersect(
                    dao.getExpensesByMinDate(minDate).toSet()
                )
                byTotalSet.intersect(total)
            }
        }
        val byMerchantSet = if(merchant == null) {
            byDateSet
        }else{
            byDateSet.intersect(dao.getExpensesByMerchant(merchant).toSet())
        }

        return byMerchantSet.map { it.toExpense() }
    }

    override suspend fun getExpensesByNotFilter(
        status: String,
        merchant: String?,
        minTotal: Double?,
        maxTotal: Double?,
        minDate: String?,
        maxDate: String?
    ): List<Expense> {
        val universalSet = dao.getExpensesByNot(status).toSet()
        val byTotalSet = if(minTotal == null && maxTotal == null) {
            universalSet
        }else {
            if(minTotal == null) {
                universalSet.intersect(dao.getExpensesByMax(maxTotal!!).toSet())
            }else if(maxTotal == null){
                universalSet.intersect(dao.getExpensesByMin(minTotal).toSet())
            }else{
                val total = dao.getExpensesByMax(maxTotal).toSet().intersect(
                    dao.getExpensesByMin(minTotal).toSet()
                )
                universalSet.intersect(total)
            }
        }
        val byDateSet = if(minDate == null && maxDate == null) {
            byTotalSet
        }else {
            if(minDate == null) {
                byTotalSet.intersect(dao.getExpensesByMaxDate(maxDate!!).toSet())
            }else if(maxDate == null){
                byTotalSet.intersect(dao.getExpensesByMinDate(minDate).toSet())
            }else{
                val total = dao.getExpensesByMaxDate(maxDate).intersect(
                    dao.getExpensesByMinDate(minDate).toSet()
                )
                byTotalSet.intersect(total)
            }
        }
        val byMerchantSet = if(merchant == null) {
            byDateSet
        }else{
            byDateSet.intersect(dao.getExpensesByMerchant(merchant).toSet())
        }

        return byMerchantSet.map { it.toExpense() }
    }
}