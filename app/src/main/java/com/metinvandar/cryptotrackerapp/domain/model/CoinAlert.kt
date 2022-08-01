package com.metinvandar.cryptotrackerapp.domain.model

import android.os.Parcelable
import com.metinvandar.cryptotrackerapp.data.local.entity.CoinAlertEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoinDomainModel(
    val id: String,
    val name: String,
    val symbol: String,
    val image: String,
    val currentPrice: Double,
    val priceChangePercentage: Double,
    val minRate: Double? = null,
    val maxRate: Double? = null
): Parcelable

fun CoinDomainModel.toEntity(minRate: Double, maxRate: Double): CoinAlertEntity {
    return CoinAlertEntity(id,name,currentPrice,minRate, maxRate)
}
