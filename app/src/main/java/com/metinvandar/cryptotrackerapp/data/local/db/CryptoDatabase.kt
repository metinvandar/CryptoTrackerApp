package com.metinvandar.cryptotrackerapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.metinvandar.cryptotrackerapp.data.local.dao.CoinDao
import com.metinvandar.cryptotrackerapp.data.local.entity.CoinRateEntity

@Database(entities = [CoinRateEntity::class], version = 1, exportSchema = false)
abstract class CryptoDatabase : RoomDatabase() {

    abstract fun coinDao(): CoinDao
}
