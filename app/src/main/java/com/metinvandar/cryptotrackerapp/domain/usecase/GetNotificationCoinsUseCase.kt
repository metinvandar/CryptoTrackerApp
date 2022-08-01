package com.metinvandar.cryptotrackerapp.domain.usecase

import com.metinvandar.cryptotrackerapp.domain.model.SimplePrice
import kotlinx.coroutines.flow.Flow

interface GetNotificationCoinsUseCase {

    suspend operator fun invoke(): Flow<List<SimplePrice>>
}
