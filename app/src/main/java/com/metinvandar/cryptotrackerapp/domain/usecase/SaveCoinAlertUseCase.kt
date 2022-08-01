package com.metinvandar.cryptotrackerapp.domain.usecase

import com.metinvandar.cryptotrackerapp.domain.model.CoinDomainModel

interface SaveCoinAlertUseCase {
    suspend operator fun invoke(coin: CoinDomainModel, minValue: Double, maxValue: Double)
}
