package com.metinvandar.cryptotrackerapp.di

import com.metinvandar.cryptotrackerapp.data.local.dao.CoinAlertDao
import com.metinvandar.cryptotrackerapp.data.local.dao.CoinHistoryDao
import com.metinvandar.cryptotrackerapp.domain.repository.CryptoRepository
import com.metinvandar.cryptotrackerapp.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetCoinsUseCase(cryptoRepository: CryptoRepository): GetCoinsUseCase {
        return GetCoinsUseCaseImpl(cryptoRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSaveCoinAlertUseCase(coinAlertDao: CoinAlertDao): SaveCoinAlertUseCase {
        return SaveCoinAlertUseCaseImpl(coinAlertDao)
    }

    @Provides
    @ViewModelScoped
    fun provideGetCoinHistoryUseCase(coinHistoryDao: CoinHistoryDao): GetCoinHistoryUseCase {
        return GetCoinHistoryUseCaseImpl(coinHistoryDao)
    }
}
