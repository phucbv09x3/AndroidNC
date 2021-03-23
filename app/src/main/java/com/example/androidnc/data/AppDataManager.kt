package com.example.androidnc.data

import com.example.androidnc.data.api.AppHeader
import com.example.androidnc.data.api.IApiHelper
import com.example.androidnc.data.local.IPreferenceHelper

import javax.inject.Inject
import javax.inject.Singleton
/**
 * Created by Phuc on 15/1/2021
 */
@Singleton
class AppDataManager @Inject constructor(
    private val mPreferencesHelper: IPreferenceHelper,
    private val mAppHeader: AppHeader,
    private val mApiHelper: IApiHelper,
) : DataManager {
    override fun get(): String {
        return mApiHelper.get()
    }

    override var token: String
        get() = mPreferencesHelper.token
        set(value) {
            mPreferencesHelper.token = value
        }



}