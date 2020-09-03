package com.bsk.cointracker.coinlist.data

import androidx.room.Entity
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
    @field:SerializedName("price_change_percentage_24h")
    val priceChangePercentage24H: String? = null,
    @field:SerializedName("description")
    val description: Map<String, String>? = null,
    @field:SerializedName("image")
    val image: Map<String, String>? = null,
    @field:SerializedName("market_data")
    val marketData: MarketData? = null
) {
    override fun toString(): String = name
}

data class MarketData(
    @field:SerializedName("current_price")
    val currentPrice: Map<String, String>? = null
) : Serializable