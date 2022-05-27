package com.impactech.expenser.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import androidx.room.Update
import com.impactech.expenser.data.local.entity.ExpenseEntity
import com.impactech.expenser.data.local.entity.MerchantEntity
import com.impactech.expenser.domain.model.Constant

@Dao
interface ExpenseDao {
    @Insert(onConflict = IGNORE)
    suspend fun addExpense(expense: ExpenseEntity)

    @Update
    suspend fun updateExpense(expense: ExpenseEntity)

    @Insert(onConflict = IGNORE)
    suspend fun addMerchant(merchant: List<MerchantEntity>)

    @Query("SELECT merchant FROM merchant")
    suspend fun getMerchants(): List<String>
    
    @Query("SELECT * FROM expense WHERE status LIKE '%' || :status || '%' AND userId = :userId")
    suspend fun getExpenses(status: String, userId: Int = Constant.userId): List<ExpenseEntity>
    
    @Query("SELECT * FROM expense WHERE status != :status AND userId = :userId")
    suspend fun getExpensesByNot(status: String, userId: Int = Constant.userId): List<ExpenseEntity>
    
    @Query("SELECT * FROM expense WHERE total >= :min AND userId = :userId")
    suspend fun getExpensesByMin(min: Double, userId: Int = Constant.userId): List<ExpenseEntity>
    
    @Query("SELECT * FROM expense WHERE total <= :max AND userId = :userId")
    suspend fun getExpensesByMax(max: Double, userId: Int = Constant.userId): List<ExpenseEntity>
    
    @Query("SELECT * FROM expense WHERE date >= :min AND userId = :userId")
    suspend fun getExpensesByMinDate(min: String, userId: Int = Constant.userId): List<ExpenseEntity>
    
    @Query("SELECT * FROM expense WHERE date <= :max AND userId = :userId")
    suspend fun getExpensesByMaxDate(max: String, userId: Int = Constant.userId): List<ExpenseEntity>
    
    @Query("SELECT * FROM expense WHERE merchant LIKE '%' || :merchant || '%' AND userId = :userId")
    suspend fun getExpensesByMerchant(merchant: String, userId: Int = Constant.userId): List<ExpenseEntity>

    @Query("SELECT SUM(total) FROM expense WHERE  status = :status AND userId = :userId")
    suspend fun getTotalByStatus(status: String = "In Progress", userId: Int = Constant.userId): Double?
    
}