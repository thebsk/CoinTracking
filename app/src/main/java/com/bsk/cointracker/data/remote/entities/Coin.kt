package com.bsk.cointracker.data.remote.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "coins")
class Coin {
    @PrimaryKey
    @field:SerializedName("id")
    lateinit var id: String

    @field:SerializedName("symbol")
    var symbol: String? = null

    @field:SerializedName("name")
    var name: String? = null

    @field:SerializedName("description")
    var description: Map<String, String>? = null

    @field:SerializedName("image")
    var image: Map<String, String>? = null

    @field:SerializedName("market_data")
    var marketData: MarketData? = null

    @field:SerializedName("hashing_algorithm")
    var hashingAlgorithm: String? = null

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