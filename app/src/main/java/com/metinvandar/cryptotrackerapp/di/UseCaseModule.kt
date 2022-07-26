package com.metinvandar.cryptotrackerapp.di

import com.metinvandar.cryptotrackerapp.domain.repository.CryptoRepository
import com.metinvandar.cryptotrackerapp.domain.usecase.GetSimplePriceUseCase
import com.metinvandar.cryptotrackerapp.domain.usecase.GetSimplePriceUseCaseImpl
import com.metinvandar.cryptotrackerapp.domain.usecase.GetCoinsUseCase
import com.metinvandar.cryptotrackerapp.domain.usecase.GetCoinsUseCaseImpl
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
    fun provideGetPricesUseCase(cryptoRepository: CryptoRepository): GetSimplePriceUseCase {
        return GetSimplePriceUseCaseImpl(cryptoRepository)
    }
}
