package com.metinvandar.cryptotrackerapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.metinvandar.cryptotrackerapp.domain.model.CoinHistory

@Entity(tableName = "coin_history")
data class CoinHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "coinId")
    val coinId: String,
    @ColumnInfo(name = "price")
    val price: Double,
    @ColumnInfo(name = "record_time")
    val recordTime: Long
)

fun CoinHistoryEntity.toCoinHistory(): CoinHistory {
    return CoinHistory(coinId, price, recordTime)
}
