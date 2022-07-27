package com.metinvandar.cryptotrackerapp.domain.usecase

import com.metinvandar.cryptotrackerapp.domain.model.CoinDomainModel

interface SaveCoinRateUseCase {
    suspend operator fun invoke(coin: CoinDomainModel, minRate: Double, maxRate: Double)
}
