package com.example.androidnc

import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.example.androidnc.data.AppDataManager
import com.example.androidnc.data.DataManager
import com.example.androidnc.data.api.ApiHelper
import com.example.androidnc.data.api.AppHeader
import com.example.androidnc.data.api.IApiHelper
import com.example.androidnc.data.local.IPreferenceHelper
import com.example.androidnc.data.local.PreferencesHelper
import com.example.androidnc.data.local.room.CoinDatabase
import com.example.bitcoin.data.local.service.CoinService
import com.example.bitcoin.data.scheduler.AppSchedulerProvider
import com.example.bitcoin.data.scheduler.ISchedulerProvider
import com.example.androidnc.ui.base.BaseActivity
import com.example.androidnc.ui.base.ViewModelFactory
import com.example.androidnc.utils.Constants
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


/**
 * Created by Phuc on 15/1/2021
 */

class App : Application(){
    private lateinit var preferencesHelper: IPreferenceHelper
    private lateinit var appHeader: AppHeader
    private lateinit var apiHelper: IApiHelper
    lateinit var dataManager: DataManager
    lateinit var factory: ViewModelFactory
    lateinit var scheduledProvider: ISchedulerProvider
    lateinit var db: CoinDatabase
    lateinit var service: CoinService

    override fun onCreate() {
        super.onCreate()
        // initNetworking()


        preferencesHelper = PreferencesHelper(this, "BasePreference")
        appHeader = AppHeader()
        apiHelper = ApiHelper(appHeader)
        dataManager = AppDataManager(preferencesHelper, appHeader, apiHelper)
        scheduledProvider = AppSchedulerProvider()
        db = CoinDatabase.init(applicationContext)
        factory = ViewModelFactory(dataManager, scheduledProvider, db)
        service= CoinService()
    }

    private fun initNetworking() {
        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(Constants.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .build()
        AndroidNetworking.initialize(applicationContext, okHttpClient)
        AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BASIC)
    }

    fun requestInject(activity: BaseActivity<*, *>) {
        activity.inject(factory, dataManager, scheduledProvider,service)
    }


}