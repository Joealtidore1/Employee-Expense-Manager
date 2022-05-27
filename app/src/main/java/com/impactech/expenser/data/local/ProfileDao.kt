package com.impactech.expenser.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.impactech.expenser.data.local.entity.EmployeeEntity

@Dao
interface ProfileDao {
    @Insert(onConflict = IGNORE)
    suspend fun addEmployee(employee: EmployeeEntity)

    @Query("SELECT * FROM employee WHERE username = :username AND password = :password")
    suspend fun getEmployee(username: String, password: String): EmployeeEntity?

    @Query("SELECT * FROM employee WHERE username = :username")
    suspend fun getEmployee(username: String): EmployeeEntity?
}