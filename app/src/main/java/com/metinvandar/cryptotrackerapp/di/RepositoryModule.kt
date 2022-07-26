package com.metinvandar.cryptotrackerapp.di

import com.metinvandar.cryptotrackerapp.data.local.dao.CoinDao
import com.metinvandar.cryptotrackerapp.data.remote.api.CryptoApi
import com.metinvandar.cryptotrackerapp.data.remote.repository.CryptoRepositoryImpl
import com.metinvandar.cryptotrackerapp.domain.repository.CryptoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideCryptoRepository(coinDao: CoinDao, cryptoApi: CryptoApi): CryptoRepository {
        return CryptoRepositoryImpl(coinDao, cryptoApi)
    }
}
