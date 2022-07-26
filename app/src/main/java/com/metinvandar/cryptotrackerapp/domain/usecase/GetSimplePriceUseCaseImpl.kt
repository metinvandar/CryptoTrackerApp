package com.metinvandar.cryptotrackerapp.domain.usecase

import com.metinvandar.cryptotrackerapp.common.Resource
import com.metinvandar.cryptotrackerapp.domain.model.CoinPriceDomainModel
import com.metinvandar.cryptotrackerapp.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSimplePriceUseCaseImpl @Inject constructor(private val cryptoRepository: CryptoRepository) :
    GetSimplePriceUseCase {
    override suspend fun invoke(coinIds: String): Flow<Resource<List<CoinPriceDomainModel>>> {
        return cryptoRepository.getPrices(coinIds)
    }
}
