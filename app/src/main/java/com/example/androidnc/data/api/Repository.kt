package com.example.androidnc.data.api

import com.example.androidnc.data.model.RootModel
import retrofit2.http.GET

interface Repository {
    @GET("/v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY=5782b99a-b379-4222-8076-fde6da7a0cf7&start=1")
    suspend fun getList(): RootModel
}