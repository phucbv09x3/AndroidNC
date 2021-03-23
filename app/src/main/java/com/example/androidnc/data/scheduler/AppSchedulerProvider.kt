package com.example.bitcoin.data.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
/**
 * Created by Phuc on 15/1/2021
 */
class AppSchedulerProvider : ISchedulerProvider {
    override val computation: Scheduler = Schedulers.computation()
    override val io: Scheduler = Schedulers.io()
    override val ui: Scheduler = AndroidSchedulers.mainThread()
}