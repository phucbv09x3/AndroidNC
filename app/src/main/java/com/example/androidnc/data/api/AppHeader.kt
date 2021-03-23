package com.example.androidnc.data.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
/**
 * Created by Phuc on 15/1/2021
 */
class AppHeader {
    companion object {
        private const val auth_prefix = "Bearer "
        private fun generateAccessToken(token: String): String {
            return auth_prefix + token
        }
    }

    @Expose
    @SerializedName("Content-Type")
    private val contentType =
        "application/x-www-form-urlencoded"//application/x-www-form-urlencoded application/json


    @Expose
    @SerializedName("Authorization")
    private var accessToken: String = ""

    fun setToken(token: String) {
        accessToken = generateAccessToken(token)
    }

    fun toContentType(): HashMap<String, String> {
        val hashMap = HashMap<String, String>()
        hashMap["Content-Type"] = contentType
        return hashMap
    }

    fun toAuthorization(): HashMap<String, String> {
        val hashMap = HashMap<String, String>()
        hashMap["Authorization"] = accessToken
        return hashMap
    }

}