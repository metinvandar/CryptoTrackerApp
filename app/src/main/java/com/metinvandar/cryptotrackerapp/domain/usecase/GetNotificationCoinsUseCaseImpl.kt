package com.metinvandar.cryptotrackerapp.domain.usecase

import com.metinvandar.cryptotrackerapp.data.local.entity.CoinAlertEntity
import com.metinvandar.cryptotrackerapp.data.local.entity.CoinHistoryEntity
import com.metinvandar.cryptotrackerapp.data.remote.models.PriceResponseWrapper
import com.metinvandar.cryptotrackerapp.domain.Resource
import com.metinvandar.cryptotrackerapp.domain.model.SimplePrice
import com.metinvandar.cryptotrackerapp.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

class GetNotificationCoinsUseCaseImpl @Inject constructor(private val cryptoRepository: CryptoRepository) :
    GetNotificationCoinsUseCase {
    override suspend fun invoke(): Flow<List<SimplePrice>> {
        val alertCoins = cryptoRepository.getAlertCoins()
        return if (alertCoins.isNotEmpty()) {
            val coinIds = alertCoins.joinToString { it.id }
            cryptoRepository.getSimplePrice(coinIds)
                .onEach {
                    if (it is Resource.Success) {
                        it.data.coinMap.forEach { (coinId, priceResponse) ->
                            val coinHistoryEntity = CoinHistoryEntity(
                                coinId = coinId,
                                price = priceResponse.price,
                                recordTime = Date().time
                            )
                            cryptoRepository.saveCoinHistory(coinHistoryEntity)
                        }
                    }
                }.map {
                    return@map if (it is Resource.Success) {
                        val notificationCoins = mutableListOf<SimplePrice>()
                        checkMinMaxValue(alertCoins, it.data, notificationCoins)
                        notificationCoins.toList()
                    } else {
                        emptyList()
                    }
                }.catch {
                    emit(emptyList())
                }
        } else {
            flow {
                emit(emptyList())
            }
        }
    }

    private fun checkMinMaxValue(
        coinEntities: List<CoinAlertEntity>,
        priceResponseWrapper: PriceResponseWrapper,
        notificationCoinList: MutableList<SimplePrice>
    ) {
        coinEntities.forEach { coinEntity ->
            val coinPrice = priceResponseWrapper.coinMap[coinEntity.id]?.price
            coinPrice?.let {
                if (it > coinEntity.maxValue || it < coinEntity.minValue) {
                    notificationCoinList.add(
                        SimplePrice(coinId = coinEntity.id, currentPrice = it)
                    )
                }
            }
        }
    }
}
