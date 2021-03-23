package com.example.androidnc.di.buidler

import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.example.androidnc.ui.main.MainActivity

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [MainFragmentProvider::class])
    internal abstract fun bindMainActivity(): MainActivity

}