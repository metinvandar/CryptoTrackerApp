package com.metinvandar.cryptotrackerapp.data.remote.models

import com.google.gson.annotations.SerializedName
import com.metinvandar.cryptotrackerapp.domain.model.CoinDomainModel

data class CoinResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("current_price")
    val currentPrice: Double,
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage: Double
)

fun CoinResponse.toDomainModel(): CoinDomainModel {
    return CoinDomainModel(id, name, symbol, image, currentPrice, priceChangePercentage)
}
