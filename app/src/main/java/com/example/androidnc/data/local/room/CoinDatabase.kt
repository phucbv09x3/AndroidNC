package com.example.androidnc.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [CoinModelRoom::class], version = 1, exportSchema = true)
abstract class CoinDatabase : RoomDatabase() {
    abstract fun coiDAO(): CoinDAO

    companion object {
        @Volatile
        var INSTANCE: CoinDatabase? = null
        private const val NAME_DB = "db_coin_room"
        fun init(context: Context): CoinDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoinDatabase::class.java,
                    NAME_DB
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}