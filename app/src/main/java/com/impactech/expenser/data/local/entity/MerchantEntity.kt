package com.impactech.expenser.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "merchant")
data class MerchantEntity(
    @PrimaryKey
    val id: Int,
    val merchant: String
)