package com.example.androidnc.ui.base

import android.os.Bundle
import com.example.androidnc.ui.base.BaseFragment
import kotlin.reflect.KClass
/**
 * Created by Phuc on 15/1/2021
 */
interface Navigators {

    fun onFragmentResumed(fragment: BaseFragment<*, *>)

    fun switchFragment(fragment: KClass<*>, bundle: Bundle? = null, addToBackStack: Boolean = true)

    fun fragmentRequestInject(fragment: BaseFragment<*, *>)

    fun showActivity(activity: Class<*>, bundle: Bundle? = null)
}