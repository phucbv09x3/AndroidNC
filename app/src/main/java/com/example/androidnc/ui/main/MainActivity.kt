package com.example.androidnc.ui.main

import android.content.res.Resources
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.example.androidnc.R
import com.example.androidnc.databinding.ActivityMainBinding
import com.example.androidnc.ui.addstt.AddSttFragment
import com.example.androidnc.ui.addstt.AddSttViewModel
import com.example.androidnc.ui.base.BaseActivity
import com.example.androidnc.ui.base.BaseFragment
import com.example.androidnc.ui.list.HomeFragment
import com.example.androidnc.ui.list.ListCoinFragment
import com.example.androidnc.ui.status.StatusFragment
import com.example.androidnc.ui.login.LoginAccFragment
import com.example.androidnc.ui.message.MessageFragment
import com.example.androidnc.ui.signup.SignUpFragment
import com.example.androidnc.utils.printLog
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.reflect.KClass

/**
 * Created by Phuc on 15/1/2021
 */

open class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    private lateinit var currentFragment: BaseFragment<*, *>


    override fun createViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun getContentView(): Int {
        return R.layout.activity_main
    }

    override fun initAction() {

    }

    override fun initData() {
        val navigation = findViewById<BottomNavigationView>(R.id.bottom_nav)
        navigation.setOnNavigationItemSelectedListener(onNav())
        switchFragment(LoginAccFragment::class, null, false)
    }


    private fun onNav() = object : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.btn_status_nav -> {
                    return true
                }
                R.id.btn_home_nav -> {
                    return true
                }
                R.id.btn_message_nav -> {
                    return true
                }
                R.id.btn_notify_nav -> {
                    return true
                }
                R.id.btn_add_nav -> {
                    return true
                }
            }
            return false
        }
    }

    override fun onFragmentResumed(fragment: BaseFragment<*, *>) {
        printLog("onFragmentResumed ${fragment.javaClass.simpleName} - ${supportFragmentManager.backStackEntryCount}")
        val showButtonBack = supportFragmentManager.backStackEntryCount > 1
        supportActionBar?.setDisplayHomeAsUpEnabled(showButtonBack)
        supportActionBar?.setDisplayShowHomeEnabled(showButtonBack)
        currentFragment = fragment
    }

    override fun switchFragment(fragment: KClass<*>, bundle: Bundle?, addToBackStack: Boolean) {
        val instanceFragment = when (fragment) {
            LoginAccFragment::class -> LoginAccFragment()
            ListCoinFragment::class -> ListCoinFragment()
            SignUpFragment::class -> SignUpFragment()
            HomeFragment::class -> HomeFragment()
            StatusFragment::class -> StatusFragment()
            MessageFragment::class -> MessageFragment()
            AddSttFragment::class -> AddSttFragment()
            else -> {
                throw Resources.NotFoundException("Fragment not fount, please check again")
            }
        }

        bundle?.let {
            instanceFragment.arguments = it
        }
        val ft = supportFragmentManager.beginTransaction()
        ft.setCustomAnimations(
            R.anim.slide_in_right,
            R.anim.slide_out_left,
            R.anim.slide_in_left,
            R.anim.slide_out_right
        )
        val tag = instanceFragment.tag
        ft.replace(R.id.main_nav_fragment, instanceFragment, tag)
        if (addToBackStack) {
            ft.addToBackStack(tag)
        }
        ft.commit()
    }


    override fun onDestroy() {
        super.onDestroy()
    }


}