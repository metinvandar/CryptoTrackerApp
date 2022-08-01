package com.metinvandar.cryptotrackerapp.domain.usecase

import com.metinvandar.cryptotrackerapp.domain.Resource
import com.metinvandar.cryptotrackerapp.domain.model.CoinDomainModel
import com.metinvandar.cryptotrackerapp.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinsUseCaseImpl @Inject constructor(
    private val cryptoRepository: CryptoRepository
): GetCoinsUseCase {
    override suspend fun invoke(): Flow<Resource<List<CoinDomainModel>>> {
        return cryptoRepository.getCoins()
    }
}
