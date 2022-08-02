package com.metinvandar.cryptotrackerapp.domain.usecase

import com.metinvandar.cryptotrackerapp.domain.model.CoinHistory
import com.metinvandar.cryptotrackerapp.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinHistoryUseCaseImpl @Inject constructor(private val cryptoRepository: CryptoRepository): GetCoinHistoryUseCase {
    override suspend fun invoke(coinId: String): Flow<List<CoinHistory>> {
        return  cryptoRepository.getCoinHistory(coinId)
    }
}
