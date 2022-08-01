package com.metinvandar.cryptotrackerapp.data.remote.repository

import com.metinvandar.cryptotrackerapp.common.extensions.handleErrors
import com.metinvandar.cryptotrackerapp.data.local.dao.CoinAlertDao
import com.metinvandar.cryptotrackerapp.data.local.dao.CoinHistoryDao
import com.metinvandar.cryptotrackerapp.data.local.entity.CoinAlertEntity
import com.metinvandar.cryptotrackerapp.data.local.entity.CoinHistoryEntity
import com.metinvandar.cryptotrackerapp.data.remote.api.CryptoApi
import com.metinvandar.cryptotrackerapp.data.remote.models.PriceResponseWrapper
import com.metinvandar.cryptotrackerapp.data.remote.models.toDomainModel
import com.metinvandar.cryptotrackerapp.domain.Resource
import com.metinvandar.cryptotrackerapp.domain.model.CoinDomainModel
import com.metinvandar.cryptotrackerapp.domain.repository.CryptoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CryptoRepositoryImpl @Inject constructor(
    private val coinAlertDao: CoinAlertDao,
    private val coinHistoryDao: CoinHistoryDao,
    private val cryptoApi: CryptoApi
) : CryptoRepository {
    override suspend fun getCoins(): Flow<Resource<List<CoinDomainModel>>> {
        return flow {
            val coins = cryptoApi.coins()
                .map { it.toDomainModel() }
            emit(
                Resource.Success(coins)
            )
        }
            .handleErrors()
            .flowOn(Dispatchers.IO)
            .onStart { emit(Resource.Loading(true)) }
            .onCompletion { emit(Resource.Loading(false)) }
    }

    override suspend fun getAlertCoins(): List<CoinAlertEntity> {
        return coinAlertDao.getAlertCoins()
    }

    override suspend fun getSimplePrice(coinIds: String): Flow<Resource<PriceResponseWrapper>> {
        return flow {
            val priceResponse = cryptoApi.simplePrice(coinIds)
            emit(Resource.Success(priceResponse))
        }.handleErrors()
            .flowOn(Dispatchers.IO)
    }

    override suspend fun saveCoinHistory(coinHistoryEntity: CoinHistoryEntity) {
        coinHistoryDao.insertHistory(coinHistoryEntity)
    }
}
