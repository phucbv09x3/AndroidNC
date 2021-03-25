package com.example.androidnc.ui.list

import com.example.androidnc.R
import com.example.androidnc.databinding.FragmentHomeBinding
import com.example.androidnc.ui.base.BaseFragment


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    override fun createViewModel(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun getResourceLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {

    }

    override fun bindViewModel() {
    }
}