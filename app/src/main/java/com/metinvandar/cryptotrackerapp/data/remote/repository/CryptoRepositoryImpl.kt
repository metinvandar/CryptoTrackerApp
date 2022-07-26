package com.metinvandar.cryptotrackerapp.data.remote.repository

import com.metinvandar.cryptotrackerapp.common.Resource
import com.metinvandar.cryptotrackerapp.common.extensions.handleErrors
import com.metinvandar.cryptotrackerapp.data.local.dao.CoinDao
import com.metinvandar.cryptotrackerapp.data.remote.api.CryptoApi
import com.metinvandar.cryptotrackerapp.data.remote.models.toDomainModel
import com.metinvandar.cryptotrackerapp.domain.model.CoinDomainModel
import com.metinvandar.cryptotrackerapp.domain.model.CoinPriceDomainModel
import com.metinvandar.cryptotrackerapp.domain.repository.CryptoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CryptoRepositoryImpl @Inject constructor(
    private val coinDao: CoinDao,
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

    override suspend fun getPrices(coinIds: String): Flow<Resource<List<CoinPriceDomainModel>>> {
        return flow {
            val prices = cryptoApi.simplePrice(coinIds).map { it.toDomainModel() }
            emit(Resource.Success(prices))
        }
            .handleErrors()
            .flowOn(Dispatchers.IO)
            .onStart { emit(Resource.Loading(true)) }
            .onCompletion { emit(Resource.Loading(false)) }
    }

}
