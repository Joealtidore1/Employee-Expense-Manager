package com.impactech.expenser.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    var date: String = "",
    var total: Double = 0.0,
    var status: String = "",
    var merchant: String = "",
    var comment: String = "",
    @ColumnInfo(name = "receipt_path")
    var receiptPath: String = "",
    var userId: Int = 0

)
