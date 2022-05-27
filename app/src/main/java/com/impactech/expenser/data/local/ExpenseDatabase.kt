package com.impactech.expenser.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        ExpenseEntity::class,
        EmployeeEntity::class,
    ],
    exportSchema = false,
    version = 1
)
abstract class ExpenseDatabase: RoomDatabase() {
    abstract val expenseDao: ExpenseDao
}