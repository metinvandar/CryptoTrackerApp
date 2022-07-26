package com.metinvandar.cryptotrackerapp.domain.usecase

interface GetCoinHistoryUseCase {

    operator fun invoke(coinId: String)
}
