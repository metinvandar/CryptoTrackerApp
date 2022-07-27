package com.metinvandar.cryptotrackerapp.domain.model

import android.os.Parcelable
import com.metinvandar.cryptotrackerapp.data.local.entity.CoinRateEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoinDomainModel(
    val id: String,
    val name: String,
    val symbol: String,
    val image: String,
    val currentPrice: Double,
    val priceChangePercentage: Double
): Parcelable

fun CoinDomainModel.toEntity(minRate: Double, maxRate: Double): CoinRateEntity {
    return CoinRateEntity(id,name,currentPrice,minRate, maxRate)
}
