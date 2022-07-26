package com.metinvandar.cryptotrackerapp.domain.repository

import com.metinvandar.cryptotrackerapp.common.Resource
import com.metinvandar.cryptotrackerapp.data.remote.models.CoinPricesResponse
import com.metinvandar.cryptotrackerapp.domain.model.CoinDomainModel
import com.metinvandar.cryptotrackerapp.domain.model.CoinPriceDomainModel
import kotlinx.coroutines.flow.Flow

interface CryptoRepository {

    suspend fun getCoins(): Flow<Resource<List<CoinDomainModel>>>

    suspend fun getPrices(coinIds: String): Flow<Resource<List<CoinPriceDomainModel>>>
}
