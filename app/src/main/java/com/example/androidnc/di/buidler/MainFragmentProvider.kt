package com.example.androidnc.di.buidler

import com.example.androidnc.ui.login.LoginAccFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Phuc on 15/1/2021
 */
@Module
abstract class MainFragmentProvider {
    @ContributesAndroidInjector
    internal abstract fun bindLoginFragment(): LoginAccFragment

}