package com.metinvandar.cryptotrackerapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.metinvandar.cryptotrackerapp.data.local.dao.CoinAlertDao
import com.metinvandar.cryptotrackerapp.data.local.dao.CoinHistoryDao
import com.metinvandar.cryptotrackerapp.data.local.entity.CoinAlertEntity
import com.metinvandar.cryptotrackerapp.data.local.entity.CoinHistoryEntity

@Database(
    entities = [CoinAlertEntity::class, CoinHistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CryptoDatabase : RoomDatabase() {

    abstract fun coinDao(): CoinAlertDao

    abstract fun coinHistoryDao(): CoinHistoryDao
}
