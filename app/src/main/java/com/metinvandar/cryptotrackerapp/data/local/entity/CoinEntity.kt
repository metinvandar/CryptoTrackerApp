package com.metinvandar.cryptotrackerapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coins")
data class CoinEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @ColumnInfo(name ="name")
    val name: String,
    @ColumnInfo(name = "price")
    val price: Double
)
