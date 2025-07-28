package com.roycemars.royalgold.feature.market.data.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.roycemars.royalgold.core.util.TimeConverter
import com.roycemars.royalgold.feature.market.data.local.CryptoEntity
import com.roycemars.royalgold.feature.market.data.remote.CryptoDto
import com.roycemars.royalgold.feature.market.domain.Crypto

fun CryptoDto.toCryptoEntity(): CryptoEntity {
    return CryptoEntity(
        id = id,
        name = name,
        symbol = symbol,
        price = quote.usd.price,
        lastUpdated = TimeConverter.isoStringToLong(lastUpdated) ?: 0,
        percentChange1h = quote.usd.percentChange1h,
        percentChange24h = quote.usd.percentChange24h,
        percentChange7d = quote.usd.percentChange7d,
        percentChange30d = quote.usd.percentChange30d
    )
}

fun CryptoEntity.toCrypto(): Crypto {
    return Crypto(
        id = id,
        name = name,
        symbol = symbol,
        price = price,
        lastUpdated = lastUpdated,
        percentChange1h = percentChange1h,
        percentChange24h = percentChange24h,
        percentChange7d = percentChange7d,
        percentChange30d = percentChange30d
    )
}