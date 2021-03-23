package com.example.androidnc.ui.base

import androidx.lifecycle.ViewModel
import com.example.androidnc.data.DataManager
import com.example.bitcoin.data.scheduler.ISchedulerProvider

import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Phuc on 15/1/2021
 */
open abstract class BaseViewModel : ViewModel() {
    lateinit var fragmentCallback: IFragmentCallback
    lateinit var navigation: Navigators

    lateinit var dataManager: DataManager
    lateinit var scheduler: ISchedulerProvider


    private val mDisposable = CompositeDisposable()

    lateinit var io: Scheduler
    lateinit var ui: Scheduler
    lateinit var computation: Scheduler

   // lateinit var progressBar: RxProgressBar

//    val trackingError: PublishSubject<ErrorResponse> by lazy {
//        PublishSubject.create<ErrorResponse>()
//    }

    fun initData(dataManager: DataManager, scheduler: ISchedulerProvider) {
        this.dataManager = dataManager
        this.scheduler = scheduler
        io = scheduler.io
        ui = scheduler.ui
        computation = scheduler.computation
       // progressBar = RxProgressBar(scheduler)
    }

    fun Disposable.addDisposable() {
        mDisposable.add(this)
    }

    override fun onCleared() {
        mDisposable.clear()
        super.onCleared()
    }

    fun onDestroyView() {
       // progressBar.reset()
        mDisposable.clear()
    }
}