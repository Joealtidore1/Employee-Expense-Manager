package com.impactech.expenser.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Employee(
    val id: Int = 0,
    var name: String = "",
    var profilePicture: String = "",
    var location: String = "",
    var department: String = "",
    var jobDescription: String = "",

)