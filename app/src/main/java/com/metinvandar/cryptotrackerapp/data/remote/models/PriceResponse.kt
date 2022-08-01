package com.metinvandar.cryptotrackerapp.data.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PriceResponse(
    @field:Json(name = "usd")
    val price: Double
)
