package com.metinvandar.cryptotrackerapp.data.remote.api

import com.metinvandar.cryptotrackerapp.data.remote.models.CoinPricesResponse
import com.metinvandar.cryptotrackerapp.data.remote.models.CoinResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApi {

    @GET("simple/price?vs_currency=usd")
    suspend fun simplePrice(@Query("ids") coinIds: String): List<CoinPricesResponse>

    @GET("coins/markets?vs_currency=usd&ids=bitcoin%2Cethereum")
    suspend fun coins(): List<CoinResponse>

}
