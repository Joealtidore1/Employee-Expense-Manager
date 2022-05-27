package com.impactech.expenser.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.impactech.expenser.data.local.entity.EmployeeEntity
import com.impactech.expenser.data.local.entity.ExpenseEntity
import com.impactech.expenser.data.local.entity.MerchantEntity

@Database(
    entities = [
        ExpenseEntity::class,
        EmployeeEntity::class,
        MerchantEntity::class,
    ],
    exportSchema = false,
    version = 1
)
abstract class ExpenseDatabase: RoomDatabase() {
    abstract val expenseDao: ExpenseDao
    abstract val profileDao: ProfileDao
}