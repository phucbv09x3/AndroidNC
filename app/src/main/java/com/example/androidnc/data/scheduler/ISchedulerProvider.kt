package com.example.bitcoin.data.scheduler

import io.reactivex.Scheduler
/**
 * Created by Phuc on 15/1/2021
 */
interface ISchedulerProvider {
    val computation: Scheduler
    val io: Scheduler
    val ui: Scheduler
}