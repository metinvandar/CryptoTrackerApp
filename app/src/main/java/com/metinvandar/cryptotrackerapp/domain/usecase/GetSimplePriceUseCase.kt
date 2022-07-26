package com.metinvandar.cryptotrackerapp.domain.usecase

import com.metinvandar.cryptotrackerapp.common.Resource
import com.metinvandar.cryptotrackerapp.domain.model.CoinPriceDomainModel
import kotlinx.coroutines.flow.Flow

interface GetSimplePriceUseCase {

    suspend operator fun invoke(coinIds: String): Flow<Resource<List<CoinPriceDomainModel>>>
}
