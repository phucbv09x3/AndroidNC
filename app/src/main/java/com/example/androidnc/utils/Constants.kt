package com.example.androidnc.utils

import android.view.View
import android.widget.LinearLayout
import com.example.androidnc.R
import com.example.androidnc.data.api.Repository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Phuc on 15/1/2021
 */
object Constants {
    const val EMPTY = ""
    const val REQUEST_TIMEOUT = 30L
    const val MILLIS_OF_TEN_MINUTE = 10 * 60 * 1000
    const val PASSWORD_LOGIN_COIN = "12345678"

    fun apiCoinRepository(url: String): Repository {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Repository::class.java)

    }



}