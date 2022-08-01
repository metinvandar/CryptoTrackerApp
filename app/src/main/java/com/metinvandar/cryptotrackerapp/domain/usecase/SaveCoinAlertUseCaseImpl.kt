package com.metinvandar.cryptotrackerapp.domain.usecase

import com.metinvandar.cryptotrackerapp.data.local.dao.CoinAlertDao
import com.metinvandar.cryptotrackerapp.domain.model.CoinDomainModel
import com.metinvandar.cryptotrackerapp.domain.model.toEntity
import javax.inject.Inject

class SaveCoinAlertUseCaseImpl @Inject constructor(private val coinAlertDao: CoinAlertDao) :
    SaveCoinAlertUseCase {

    override suspend fun invoke(coin: CoinDomainModel, minValue: Double, maxValue: Double) {
        coinAlertDao.insertCoin(coin.toEntity(minValue, maxValue))
    }
}
