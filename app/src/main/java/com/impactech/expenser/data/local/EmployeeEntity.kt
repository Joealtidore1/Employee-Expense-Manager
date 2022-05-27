package com.impactech.expenser.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee")
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String = "",
    @ColumnInfo(name = "profile_picture")
    var profilePicture: String = "",
    var location: String = "",
    var department: String = "",
    @ColumnInfo(name = "job_description")
    var jobDescription: String = "",

)
