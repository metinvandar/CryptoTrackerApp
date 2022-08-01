package com.metinvandar.cryptotrackerapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_alert")
data class CoinAlertEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @ColumnInfo(name ="name")
    val name: String,
    @ColumnInfo(name = "price")
    val price: Double,
    @ColumnInfo(name = "minValue")
    val minValue: Double,
    @ColumnInfo(name = "maxValue")
    val maxValue: Double
)
