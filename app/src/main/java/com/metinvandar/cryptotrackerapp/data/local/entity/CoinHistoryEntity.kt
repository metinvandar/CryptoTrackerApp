package com.metinvandar.cryptotrackerapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_history")
data class CoinHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "coinId")
    val coinId: Int = 0,
    @ColumnInfo(name = "price")
    val price: Double,
    @ColumnInfo(name = "record_time")
    val recordTime: Long
)
