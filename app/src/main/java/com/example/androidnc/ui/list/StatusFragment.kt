package com.example.androidnc.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidnc.R
import com.example.androidnc.databinding.FragmentStatusBinding
import com.example.androidnc.ui.base.BaseFragment

class StatusFragment : BaseFragment<StatusViewModel,FragmentStatusBinding>() {
    override fun createViewModel(): Class<StatusViewModel> {
        return StatusViewModel::class.java
    }

    override fun getResourceLayout(): Int {
       return R.layout.fragment_status
    }

    override fun initView() {
    }

    override fun bindViewModel() {
    }
}