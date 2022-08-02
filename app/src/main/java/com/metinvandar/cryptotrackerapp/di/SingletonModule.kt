package com.metinvandar.cryptotrackerapp.di

import android.content.Context
import android.content.res.Configuration
import com.metinvandar.cryptotrackerapp.common.ResourceManager
import com.metinvandar.cryptotrackerapp.common.ResourceManagerImpl
import com.metinvandar.cryptotrackerapp.data.local.dao.CoinAlertDao
import com.metinvandar.cryptotrackerapp.data.local.dao.CoinHistoryDao
import com.metinvandar.cryptotrackerapp.data.remote.api.CryptoApi
import com.metinvandar.cryptotrackerapp.data.remote.repository.CryptoRepositoryImpl
import com.metinvandar.cryptotrackerapp.domain.repository.CryptoRepository
import com.metinvandar.cryptotrackerapp.domain.usecase.GetNotificationCoinsUseCase
import com.metinvandar.cryptotrackerapp.domain.usecase.GetNotificationCoinsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {

    @Provides
    @Singleton
    fun provideCryptoRepository(
        coinAlertDao: CoinAlertDao,
        cryptoApi: CryptoApi,
        coinHistoryDao: CoinHistoryDao
    ): CryptoRepository {
        return CryptoRepositoryImpl(coinAlertDao, coinHistoryDao, cryptoApi)
    }

    @Provides
    @Singleton
    fun provideSimplePriceUseCase(repository: CryptoRepository): GetNotificationCoinsUseCase {
        return GetNotificationCoinsUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideResourceManager(@ApplicationContext context: Context): ResourceManager {
        val config = Configuration(context.resources.configuration)
        config.setLocale(Locale.getDefault())
        val configContext = context.createConfigurationContext(config)
        return ResourceManagerImpl(configContext)
    }
}
