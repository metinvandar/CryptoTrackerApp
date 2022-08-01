package com.metinvandar.cryptotrackerapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.metinvandar.cryptotrackerapp.data.local.entity.CoinAlertEntity

@Dao
interface CoinAlertDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoin(user: CoinAlertEntity)

    @Query("SELECT * FROM coin_alert")
    suspend fun getAlertCoins(): List<CoinAlertEntity>
}
