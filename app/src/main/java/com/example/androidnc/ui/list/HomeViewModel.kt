package com.example.androidnc.ui.list

import com.example.androidnc.ui.base.BaseViewModel

class HomeViewModel : BaseViewModel() {
    fun clickButtonHome(){
        navigation.switchFragment(StatusFragment::class)
    }
    fun clickButtonMessage(){

    }
    fun clickButtonAdd(){

    }
    fun clickButtonNotify(){

    }

    fun clickButtonMenu(){

    }
}