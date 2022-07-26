package com.metinvandar.cryptotrackerapp.data.remote.models

import com.google.gson.annotations.SerializedName
import com.metinvandar.cryptotrackerapp.domain.model.CoinPriceDomainModel

data class CoinPricesResponse(@SerializedName("usd") val price: Double)

fun CoinPricesResponse.toDomainModel(): CoinPriceDomainModel {
    return CoinPriceDomainModel(price)
}
