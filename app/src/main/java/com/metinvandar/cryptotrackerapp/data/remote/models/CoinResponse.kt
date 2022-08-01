package com.metinvandar.cryptotrackerapp.data.remote.models

import com.metinvandar.cryptotrackerapp.domain.model.CoinDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoinResponse(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "symbol")
    val symbol: String,
    @field:Json(name = "image")
    val image: String,
    @field:Json(name = "current_price")
    val currentPrice: Double,
    @field:Json(name = "price_change_percentage_24h")
    val priceChangePercentage: Double
)

fun CoinResponse.toDomainModel(): CoinDomainModel {
    return CoinDomainModel(id, name, symbol, image, currentPrice, priceChangePercentage)
}
