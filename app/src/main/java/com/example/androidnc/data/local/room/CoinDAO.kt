package com.example.androidnc.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CoinDAO {
    @Insert
    suspend fun insertCoin(coinModelRoom: MutableList<CoinModelRoom>)

    @Query("SELECT  * FROM ${CoinModelRoom.TABLE_COIN_NAME}")
    suspend fun getListCoinRoom(): MutableList<CoinModelRoom>?

    @Update
    fun updateCoin(coinModelRoom: CoinModelRoom)

    @Query("update CoinModelRoom set ${CoinModelRoom.COLUMN_CHECK_PRICE}=:chang where id=:id ")
    fun updateCoinUpDown(chang: String, id: Int)

    @Update
    suspend fun updateListChange(list: MutableList<CoinModelRoom>)
}