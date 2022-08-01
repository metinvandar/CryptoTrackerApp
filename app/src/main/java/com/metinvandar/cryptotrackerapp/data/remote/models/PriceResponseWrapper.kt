package com.metinvandar.cryptotrackerapp.data.remote.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PriceResponseWrapper(
    val coinMap: Map<String, PriceResponse>
)
