package com.bsk.cointracker.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bsk.cointracker.data.remote.entities.Coin


@Dao
interface CoinDao {

    @Query("SELECT * FROM coins ORDER BY name DESC")
    fun getCoins(): LiveData<List<Coin>>

    @Query("SELECT * FROM coins WHERE id = :id")
    fun getCoin(id: String): LiveData<Coin>

    @Query("SELECT * FROM coins WHERE name LIKE '%' || :query || '%' OR symbol LIKE '%' || :query || '%' ORDER BY name DESC")
    fun searchCoin(query: String): LiveData<List<Coin>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(coins: List<Coin>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(coin: Coin)
}
