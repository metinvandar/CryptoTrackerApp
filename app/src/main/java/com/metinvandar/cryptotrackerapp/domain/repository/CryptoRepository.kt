package com.metinvandar.cryptotrackerapp.domain.repository

import com.metinvandar.cryptotrackerapp.domain.Resource
import com.metinvandar.cryptotrackerapp.data.local.entity.CoinAlertEntity
import com.metinvandar.cryptotrackerapp.data.local.entity.CoinHistoryEntity
import com.metinvandar.cryptotrackerapp.data.remote.models.PriceResponseWrapper
import com.metinvandar.cryptotrackerapp.domain.model.CoinDomainModel
import com.metinvandar.cryptotrackerapp.domain.model.CoinHistory
import kotlinx.coroutines.flow.Flow

interface CryptoRepository {

    suspend fun getCoins(): Flow<Resource<List<CoinDomainModel>>>

    suspend fun getAlertCoins(): List<CoinAlertEntity>

    suspend fun getSimplePrice(coinIds: String): Flow<Resource<PriceResponseWrapper>>

    suspend fun saveCoinHistory(coinHistoryEntity: CoinHistoryEntity)

    suspend fun getCoinHistory(coinId: String): Flow<List<CoinHistory>>
}
