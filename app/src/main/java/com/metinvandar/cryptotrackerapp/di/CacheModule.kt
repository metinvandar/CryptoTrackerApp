package com.metinvandar.cryptotrackerapp.di

import android.content.Context
import androidx.room.Room
import com.metinvandar.cryptotrackerapp.common.Constants.DATABASE_NAME
import com.metinvandar.cryptotrackerapp.data.local.dao.CoinAlertDao
import com.metinvandar.cryptotrackerapp.data.local.dao.CoinHistoryDao
import com.metinvandar.cryptotrackerapp.data.local.db.CryptoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): CryptoDatabase =
        Room.databaseBuilder(context, CryptoDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideCoinAlertDao(db: CryptoDatabase): CoinAlertDao = db.coinDao()

    @Singleton
    @Provides
    fun provideCoinHistoryDao(db: CryptoDatabase): CoinHistoryDao = db.coinHistoryDao()
}
