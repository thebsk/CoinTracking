package com.bsk.cointracker.data.remote.entities

import androidx.room.Entity
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

    val largeImage
        get() = image?.get("large") ?: ""

    val price
        get() = marketData?.currentPrice?.get("try") ?: ""

    val desc
        get() = description?.get("tr") ?: ""
}

class MarketData : Serializable {
    @field:SerializedName("current_price")
    val currentPrice: Map<String, String>? = null

    @field:SerializedName("price_change_percentage_24h")
    val priceChangePercentage24H: String? = null
}