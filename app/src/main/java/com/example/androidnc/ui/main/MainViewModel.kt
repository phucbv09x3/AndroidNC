package com.example.androidnc.ui.main

import androidx.lifecycle.MutableLiveData
import com.example.androidnc.ui.base.BaseViewModel

/**
 * Created by Phuc on 15/1/2021
 */
class MainViewModel : BaseViewModel() {


    fun saveToken(token: String) {
       // dataManager.token = token
    }

    fun getToken(): String {
        return dataManager.token
    }


}