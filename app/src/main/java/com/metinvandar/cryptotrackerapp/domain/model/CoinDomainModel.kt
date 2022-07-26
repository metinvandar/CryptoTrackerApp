package com.metinvandar.cryptotrackerapp.domain.model

data class CoinDomainModel(
    val id: String,
    val name: String,
    val symbol: String,
    val image: String,
    val currentPrice: Double,
    val priceChangePercentage: Double
)
