package com.metinvandar.cryptotrackerapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.metinvandar.cryptotrackerapp.data.local.entity.CoinHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(coinHistoryEntity: CoinHistoryEntity)

    @Query("SELECT * FROM coin_history where coinId = :coinId")
    suspend fun getCoinHistory(coinId: String): List<CoinHistoryEntity>
}
