package com.metinvandar.cryptotrackerapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_rate")
data class CoinRateEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @ColumnInfo(name ="name")
    val name: String,
    @ColumnInfo(name = "price")
    val price: Double,
    @ColumnInfo(name = "minRate")
    val minRate: Double,
    @ColumnInfo(name = "maxRate")
    val maxRate: Double
)
