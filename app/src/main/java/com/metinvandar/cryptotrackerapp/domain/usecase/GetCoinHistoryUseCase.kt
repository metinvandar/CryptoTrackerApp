package com.metinvandar.cryptotrackerapp.domain.usecase

import com.metinvandar.cryptotrackerapp.domain.model.CoinHistory
import kotlinx.coroutines.flow.Flow

interface GetCoinHistoryUseCase {

    suspend operator fun invoke(coinId: String): Flow<List<CoinHistory>>
}
