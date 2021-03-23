package com.example.androidnc.di.module

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import com.example.androidnc.data.AppDataManager
import com.example.androidnc.data.DataManager
import com.example.androidnc.data.api.ApiHelper
import com.example.androidnc.data.api.AppHeader
import com.example.androidnc.data.api.IApiHelper
import com.example.androidnc.data.local.IPreferenceHelper
import com.example.androidnc.data.local.PreferenceInfo
import com.example.androidnc.data.local.PreferencesHelper
import com.example.androidnc.data.local.room.CoinDatabase
import com.example.bitcoin.data.local.service.CoinService
import com.example.bitcoin.data.scheduler.AppSchedulerProvider
import com.example.bitcoin.data.scheduler.ISchedulerProvider
import com.example.androidnc.ui.base.ViewModelFactory
import javax.inject.Singleton

/**
 * Created by Phuc on 15/1/2021
 */


@Module
class AppModule {
    @Provides
    @PreferenceInfo
    internal fun providePreferenceName(): String {
        return "KeyPreference"
    }

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun providePreferenceHelper(preferencesHelper: PreferencesHelper): IPreferenceHelper {
        return preferencesHelper
    }

    @Provides
    @Singleton
    internal fun provideAppHeader(): AppHeader {
        return AppHeader()
    }

    @Provides
    @Singleton
    internal fun provideAppApiHelper(appHeader: AppHeader): IApiHelper {
        return ApiHelper(appHeader)
    }

    @Provides
    @Singleton
     fun provideDataManager(appDataManager: AppDataManager): DataManager {
        return appDataManager
    }

    @Provides
    @Singleton
    internal fun provideScheduler(): ISchedulerProvider {
        return AppSchedulerProvider()
    }

    @Provides
    @Singleton
    fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory {
        return factory
    }

    @Provides
    @Singleton
    fun coinDatabase(application: Application): CoinDatabase {
        return CoinDatabase.init(application)
    }

    @Provides
    @Singleton
    fun service(): CoinService {
        return CoinService()
    }
}