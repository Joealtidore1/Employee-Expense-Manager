package com.impactech.expenser.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class Expense(
    val id:Int = 0,
    var date: String = "",
    var total: Double = 0.0,
    var merchant : String = "",
    var status: String = "New",
    var comment: String = "",
    var receiptPath: String = ""

): Serializable
