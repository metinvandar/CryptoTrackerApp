package com.metinvandar.cryptotrackerapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.metinvandar.cryptotrackerapp.data.local.entity.CoinRateEntity

@Dao
interface CoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoin(user: CoinRateEntity)
}
