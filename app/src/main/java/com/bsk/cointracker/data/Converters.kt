package com.bsk.cointracker.data

import androidx.room.TypeConverter
import com.bsk.cointracker.coinlist.data.MarketData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Type converters to allow Room to reference complex data types.
 */
object Converters {
    @TypeConverter
    @JvmStatic
    fun stringToMap(value: String?): Map<String, String>? {
        return Gson().fromJson(value, object : TypeToken<Map<String, String>?>() {}.type)
    }

    @TypeConverter
    @JvmStatic
    fun mapToString(value: Map<String, String>?): String? {
        return if (value == null) null else Gson().toJson(value)
    }

    @TypeConverter
    @JvmStatic
    fun marketToString(value: MarketData?): String? {
        return if (value == null) null else Gson().toJson(value)
    }

    @TypeConverter
    @JvmStatic
    fun stringToMarket(value: String?): MarketData? {
        return Gson().fromJson(value, object : TypeToken<MarketData?>() {}.type)
    }
}