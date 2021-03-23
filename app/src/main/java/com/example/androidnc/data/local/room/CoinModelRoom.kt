package com.example.androidnc.data.local.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = CoinModelRoom.TABLE_COIN_NAME)
data class CoinModelRoom @JvmOverloads constructor(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID) var id: Int,
    @ColumnInfo(name = COLUMN_NAME) var name: String,
    @ColumnInfo(name = COLUMN_SYMBOL) var symbol: String,
    @ColumnInfo(name = COLUMN_SLUG) var slug: String,
    @ColumnInfo(name = COLUMN_PRICE) var price: Double,
    @ColumnInfo(name = COLUMN_CHECK_PRICE) var isCheckPrice: String,
    @Ignore var isNeedUpdate: Boolean = false
) {
    companion object {
        const val TABLE_COIN_NAME = "CoinModelRoom"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_SYMBOL = "symbol"
        const val COLUMN_SLUG = "slug"
        const val COLUMN_PRICE = "price"
        const val COLUMN_CHECK_PRICE = "isCheckPrice"
    }
}

