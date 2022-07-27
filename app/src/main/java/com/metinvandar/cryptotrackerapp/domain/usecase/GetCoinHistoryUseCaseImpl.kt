package com.metinvandar.cryptotrackerapp.domain.usecase

import com.metinvandar.cryptotrackerapp.data.local.dao.CoinHistoryDao
import com.metinvandar.cryptotrackerapp.domain.model.CoinHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCoinHistoryUseCaseImpl @Inject constructor(private val coinHistoryDao: CoinHistoryDao): GetCoinHistoryUseCase {
    override suspend fun invoke(coinId: String): Flow<List<CoinHistory>> {
        return flow {
            val coinHistory = coinHistoryDao.getCoinHistory(coinId).map {
                CoinHistory(it.price, it.recordTime)
            }
            emit(coinHistory)
        }
    }
}
