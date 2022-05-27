package com.impactech.expenser.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class Employee(
    val id: Int = 0,
    var name: String = "",
    var profilePicture: String = "",
    var location: String = "",
    var department: String = "",
    var jobDescription: String = "",
    var username: String = "",
    var password: String? = null
): Serializable

object Constant {
    var userId = 0
    var user: Employee? = null
}
