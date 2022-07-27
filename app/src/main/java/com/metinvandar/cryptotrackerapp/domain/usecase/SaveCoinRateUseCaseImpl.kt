package com.metinvandar.cryptotrackerapp.domain.usecase

import com.metinvandar.cryptotrackerapp.data.local.dao.CoinDao
import com.metinvandar.cryptotrackerapp.domain.model.CoinDomainModel
import com.metinvandar.cryptotrackerapp.domain.model.toEntity
import javax.inject.Inject

class SaveCoinRateUseCaseImpl @Inject constructor(private val coinDao: CoinDao) :
    SaveCoinRateUseCase {

    override suspend fun invoke(coin: CoinDomainModel, minRate: Double, maxRate: Double) {
        coinDao.insertCoin(coin.toEntity(minRate, maxRate))
    }
}
