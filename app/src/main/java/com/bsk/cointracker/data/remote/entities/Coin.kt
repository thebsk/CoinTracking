package com.bsk.cointracker.data.remote.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "coins")
data class Coin(
    @PrimaryKey
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("symbol")
    val symbol: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("description")
    val description: Map<String, String>? = null,
    @field:SerializedName("image")
    val image: Map<String, String>? = null,
    @field:SerializedName("market_data")
    val marketData: MarketData? = null,
    @field:SerializedName("hashing_algorithm")
    val hashingAlgorithm: String? = null
) {
    override fun toString(): String = name

    @Ignore
    val largeImage = image?.get("large") ?: ""

    @Ignore
    val price = marketData?.currentPrice?.get("try") ?: ""

    @Ignore
    val desc = description?.get("tr") ?: ""
}

data class MarketData(
    @field:SerializedName("current_price")
    val currentPrice: Map<String, String>? = null,
    @field:SerializedName("price_change_percentage_24h")
    val priceChangePercentage24H: String? = null
) : Serializable