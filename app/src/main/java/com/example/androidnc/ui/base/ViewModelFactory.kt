package com.example.androidnc.ui.base

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidnc.data.DataManager
import com.example.androidnc.data.local.room.CoinDatabase
import com.example.androidnc.ui.list.HomeViewModel
import com.example.bitcoin.data.scheduler.ISchedulerProvider
import com.example.androidnc.ui.list.ListCoinViewModel
import com.example.androidnc.ui.list.StatusViewModel
import com.example.androidnc.ui.login.LoginAccViewModel
import com.example.androidnc.ui.main.MainViewModel
import com.example.androidnc.ui.signup.SignUpViewModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Phuc on 15/1/2021
 */
@Singleton
class ViewModelFactory @Inject constructor(
    private val mDataManager: DataManager,
    private val mScheduler: ISchedulerProvider,
    private val db: CoinDatabase
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var viewModel = when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel() as T
            modelClass.isAssignableFrom(LoginAccViewModel::class.java) -> LoginAccViewModel() as T
            modelClass.isAssignableFrom(ListCoinViewModel::class.java) -> ListCoinViewModel(db) as T
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> SignUpViewModel() as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel() as T
            modelClass.isAssignableFrom(StatusViewModel::class.java) -> StatusViewModel() as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
        if (viewModel is BaseViewModel) {
            viewModel.initData(mDataManager, mScheduler)
        }
        return viewModel
    }
}