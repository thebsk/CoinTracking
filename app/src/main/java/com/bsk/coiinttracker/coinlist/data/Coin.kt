package com.bsk.coiinttracker.coinlist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "coins")
data class Coin(
    @PrimaryKey
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("symbol")
    val symbol: String,
    @field:SerializedName("name")
    val name: String
) {
    override fun toString(): String = name
}