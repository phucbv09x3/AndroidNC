package com.example.androidnc.ui.base

import android.content.Context
/**
 * Created by Phuc on 15/1/2021
 */
interface IFragmentCallback {
    fun getContext(): Context
    fun showLoading()
    fun hideLoading()
}