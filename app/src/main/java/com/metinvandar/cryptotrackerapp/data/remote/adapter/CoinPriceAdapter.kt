package com.metinvandar.cryptotrackerapp.data.remote.adapter

import com.metinvandar.cryptotrackerapp.data.remote.models.PriceResponse
import com.metinvandar.cryptotrackerapp.data.remote.models.PriceResponseWrapper
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter

class CoinPriceAdapter : JsonAdapter<PriceResponseWrapper>() {
    @FromJson
    override fun fromJson(reader: JsonReader): PriceResponseWrapper {
        val map: MutableMap<String, PriceResponse> = mutableMapOf()

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.peek()) {
                JsonReader.Token.NAME -> {
                    val fieldName = reader.nextName()
                    reader.beginObject()
                    var price = 0.0
                    while (reader.hasNext()) {
                        when (reader.nextName()) {
                            "usd" -> price = reader.nextDouble()
                            else -> reader.skipValue()
                        }
                    }
                    reader.endObject()
                    map[fieldName] = PriceResponse(price)
                }
                else -> reader.skipValue()
            }
        }
        reader.endObject()

        return PriceResponseWrapper(map)
    }

    override fun toJson(writer: JsonWriter, value: PriceResponseWrapper?) {

    }
}
