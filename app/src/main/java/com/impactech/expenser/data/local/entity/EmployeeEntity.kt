package com.impactech.expenser.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "employee",
)
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String = "Demo",
    var username: String = "demo",
    var password: String = "demo",
    @ColumnInfo(name = "profile_picture")
    var profilePicture: String = "",
    var location: String = "Demo",
    var department: String = "Demo",
    @ColumnInfo(name = "job_description")
    var jobDescription: String = "Demo",

)
