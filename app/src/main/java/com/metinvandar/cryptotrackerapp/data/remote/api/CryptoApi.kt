package com.metinvandar.cryptotrackerapp.data.remote.api

import com.metinvandar.cryptotrackerapp.data.remote.models.CoinResponse
import com.metinvandar.cryptotrackerapp.data.remote.models.PriceResponseWrapper
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApi {

    @GET("simple/price?vs_currencies=usd")
    suspend fun simplePrice(@Query("ids") coinIds: String): PriceResponseWrapper

    @GET("coins/markets?vs_currency=usd&ids=bitcoin%2Cethereum%2Cripple")
    suspend fun coins(): List<CoinResponse>

}
