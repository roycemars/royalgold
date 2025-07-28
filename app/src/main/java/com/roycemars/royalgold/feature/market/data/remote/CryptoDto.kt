package com.roycemars.royalgold.feature.market.data.remote

import com.google.gson.annotations.SerializedName

data class CryptoDto(
    val id: Int,
    val name: String,
    val symbol: String,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("quote")
    val quote: QuoteDto
)

data class QuoteDto(
    @SerializedName("USD")
    val usd: UsdDto
) {
    data class UsdDto (
        val price: Double,
        @SerializedName("percent_change_1h")
        val percentChange1h: Double,
        @SerializedName("percent_change_24h")
        val percentChange24h: Double,
        @SerializedName("percent_change_7d")
        val percentChange7d: Double,
        @SerializedName("percent_change_30d")
        val percentChange30d: Double
    )
}