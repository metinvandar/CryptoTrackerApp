package com.metinvandar.cryptotrackerapp.domain.usecase

import com.metinvandar.cryptotrackerapp.domain.Resource
import com.metinvandar.cryptotrackerapp.domain.model.CoinDomainModel
import kotlinx.coroutines.flow.Flow

interface GetCoinsUseCase {
    suspend operator fun invoke(): Flow<Resource<List<CoinDomainModel>>>
}
