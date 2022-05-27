package com.impactech.expenser.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import androidx.room.Update
import com.impactech.expenser.domain.model.Expense

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
    
    @Query("SELECT * FROM expense WHERE status LIKE '%' || :status || '%'")
    suspend fun getExpenses(status: String): List<ExpenseEntity>
    
    @Query("SELECT * FROM expense WHERE status != :status")
    suspend fun getExpensesByNot(status: String): List<ExpenseEntity>
    
    @Query("SELECT * FROM expense WHERE total >= :min")
    suspend fun getExpensesByMin(min: Double): List<ExpenseEntity>
    
    @Query("SELECT * FROM expense WHERE total <= :max")
    suspend fun getExpensesByMax(max: Double): List<ExpenseEntity>
    
    @Query("SELECT * FROM expense WHERE date >= :min")
    suspend fun getExpensesByMinDate(min: String): List<ExpenseEntity>
    
    @Query("SELECT * FROM expense WHERE date <= :max")
    suspend fun getExpensesByMaxDate(max: String): List<ExpenseEntity>
    
    @Query("SELECT * FROM expense WHERE merchant LIKE '%' || :merchant || '%'")
    suspend fun getExpensesByMerchant(merchant: String): List<ExpenseEntity>

    /*@Query("SELECT * FROM expense")
    suspend fun getExpenses(): List<ExpenseEntity>

    @Query("SELECT * FROM expense WHERE status = :status")
    suspend fun getExpensesByStatus(status: String): List<ExpenseEntity>

    @Query("SELECT * FROM expense WHERE status != :status")
    suspend fun getExpensesByStatusNot(status: String): List<ExpenseEntity>

    @Query("SELECT * FROM expense WHERE merchant = :merchant")
    suspend fun getExpensesByMerchant(merchant: String): List<ExpenseEntity>

    @Query("SELECT * FROM expense WHERE merchant = :merchant AND status = :status")
    suspend fun getExpensesByMerchantAndStatus(merchant: String, status: String): List<ExpenseEntity>

    @Query("SELECT * FROM expense WHERE merchant = :merchant AND status != :status")
    suspend fun getExpensesByMerchantAndStatusNot(merchant: String, status: String): List<ExpenseEntity>

    @Query("SELECT * FROM expense WHERE total BETWEEN :min AND :max")
    suspend fun getExpensesByTotal(min: Double, max: Double): List<ExpenseEntity>

    @Query("SELECT * FROM expense WHERE total BETWEEN :min AND :max AND status = :status")
    suspend fun getExpensesByTotalAndStatus(min: Double, max: Double, status: String): List<ExpenseEntity>

    @Query("SELECT * FROM expense WHERE total BETWEEN :min AND :max AND status != :status")
    suspend fun getExpensesByTotalAndStatusNot(min: Double, max: Double, status: String): List<ExpenseEntity>

    @Query("SELECT * FROM expense WHERE total BETWEEN :min AND :max AND status = :status AND merchant = :merchant")
    suspend fun getExpensesByTotalAndStatusAndMerchant(min: Double, max: Double, status: String, merchant: String): List<ExpenseEntity>

    @Query("SELECT * FROM expense WHERE total BETWEEN :min AND :max AND status != :status AND merchant = :merchant")
    suspend fun getExpensesByTotalAndStatusNotAndMerchant(min: Double, max: Double, status: String, merchant: String): List<ExpenseEntity>

    @Query("SELECT * FROM expense WHERE total BETWEEN :min AND :max AND merchant != :merchant")
    suspend fun getExpensesByTotalAndMerchantNot(min: Double, max: Double, merchant: String): List<ExpenseEntity>

    @Query("SELECT * FROM expense WHERE total  >=  :min  AND merchant = :merchant ")
    suspend fun getExpensesByTotalAndMerchant(min: Double, merchant: String): List<ExpenseEntity>

    @Query("SELECT * FROM expense WHERE total  <=  :max  AND merchant = :merchant")
    suspend fun getExpensesByMaxTotalAndMerchant(max: Double, merchant: String): List<ExpenseEntity>


    @Query("SELECT * FROM expense WHERE total  <=  :max  AND merchant = :merchant AND status = :status")
    suspend fun getExpensesByMaxTotalAndMerchantAndStatus(max: Double, merchant: String, status: String): List<ExpenseEntity>
    
    @Query("SELECT * FROM expense WHERE total  <=  :max  AND merchant = :merchant AND status != :status")
    suspend fun getExpensesByMaxTotalAndMerchantAndStatusNot(max: Double, merchant: String, status: String): List<ExpenseEntity>

    @Query("SELECT * FROM expense WHERE total  >=  :min  AND merchant = :merchant AND status = :status")
    suspend fun getExpensesByMinTotalAndMerchantAndStatus(min: Double, merchant: String, status: String): List<ExpenseEntity>
    
    @Query("SELECT * FROM expense WHERE total  >=  :min  AND merchant = :merchant AND status != :status")
    suspend fun getExpensesByMinTotalAndMerchantAndStatusNot(min: Double, merchant: String, status: String): List<ExpenseEntity>

    @Query("SELECT * FROM expense WHERE total >= :min ")
    suspend fun getExpensesByTotalGreaterThan(min: Double): List<ExpenseEntity>

    @Query("SELECT * FROM expense WHERE total <= :max ")
    suspend fun getExpensesByTotalLessThan(max: Double): List<ExpenseEntity>
    
    //date
    @Query("SELECT * FROM expense WHERE date BETWEEN :min AND :max")
    suspend fun getExpensesByDate(min: String, max: String): List<ExpenseEntity>
    
    @Query("SELECT * FROM expense WHERE date >= :min")
    suspend fun getExpensesByDateGreaterThan(min: String): List<ExpenseEntity>
    
    @Query("SELECT * FROM expense WHERE date <= :to")
    suspend fun getExpensesByDateLessThan(to: String): List<ExpenseEntity>
    
    @Query("SELECT * FROM expense WHERE date >= :min AND status = :status")
    suspend fun getExpensesByMinDateAndStatus(min: String, status: String): List<ExpenseEntity>
    
    @Query("SELECT * FROM expense WHERE date >= :min AND status != :status")
    suspend fun getExpensesByMinDateAndStatusNot(min: String, status: String): List<ExpenseEntity>

    @Query("SELECT * FROM expense WHERE date <= :max AND status = :status")
    suspend fun getExpensesByMaxDateAndStatus(max: String, status: String): List<ExpenseEntity>

    @Query("SELECT * FROM expense WHERE date <= :max AND status != :status")
    suspend fun getExpensesByMaxDateAndStatusNot(max: String, status: String): List<ExpenseEntity>
    
    @Query("SELECT * FROM expense WHERE date BETWEEN :min AND :to AND status = :status")
    suspend fun getExpensesByDateAndStatus(min: String, to: String, status: String): List<ExpenseEntity>
    
    @Query("SELECT * FROM expense WHERE date BETWEEN :min AND :to  AND status != :status")
    suspend fun getExpensesByDateAndStatusNot(min: String, to: String, status: String): List<ExpenseEntity>

    //date and total
    
    //date and merchant
    @Query("SELECT * FROM expense WHERE date BETWEEN :min AND :max AND merchant = :merchant")
    suspend fun getExpensesByDateAndMerchant(min: String, max: String, merchant: String): List<ExpenseEntity>

    @Query("SELECT * FROM expense WHERE date >= :min AND merchant = :merchant")
    suspend fun getExpensesByDateGreaterThanAndMerchant(min: String, merchant: String): List<ExpenseEntity>

    @Query("SELECT * FROM expense WHERE date <= :to AND merchant = :merchant")
    suspend fun getExpensesByDateLessThanAndMerchant(to: String, merchant: String): List<ExpenseEntity>

    @Query("SELECT * FROM expense WHERE date >= :min AND status = :status AND merchant = :merchant")
    suspend fun getExpensesByMinDateAndStatusAndMerchant(min: String, status: String, merchant: String): List<ExpenseEntity>

    @Query("SELECT * FROM expense WHERE date >= :min AND status != :status AND merchant = :merchant")
    suspend fun getExpensesByMinDateAndStatusNotAndMerchant(min: String, status: String, merchant: String): List<ExpenseEntity>

    @Query("SELECT * FROM expense WHERE date <= :max AND status = :status AND merchant = :merchant")
    suspend fun getExpensesByMaxDateAndStatusAndMerchant(max: String, status: String, merchant: String): List<ExpenseEntity>

    @Query("SELECT * FROM expense WHERE date <= :max AND status != :status AND merchant = :merchant")
    suspend fun getExpensesByMaxDateAndStatusNotAndMerchant(max: String, status: String, merchant: String): List<ExpenseEntity>

    @Query("SELECT * FROM expense WHERE date BETWEEN :min AND :to AND status = :status AND merchant = :merchant")
    suspend fun getExpensesByDateAndStatusAndMerchant(min: String, to: String, status: String, merchant: String): List<ExpenseEntity>

    @Query("SELECT * FROM expense WHERE date BETWEEN :min AND :to  AND status != :status AND merchant = :merchant")
    suspend fun getExpensesByDateAndStatusNotAndMerchant(min: String, to: String, status: String, merchant: String): List<ExpenseEntity>
    */
    
}