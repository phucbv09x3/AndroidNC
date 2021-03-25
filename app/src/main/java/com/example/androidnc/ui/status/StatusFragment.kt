package com.example.androidnc.ui.status

import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidnc.R
import com.example.androidnc.databinding.FragmentStatusBinding
import com.example.androidnc.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_status.*
import java.util.*

class StatusFragment : BaseFragment<StatusViewModel,FragmentStatusBinding>() {
    lateinit var adapter:StatusAdapter
    override fun createViewModel(): Class<StatusViewModel> {
        return StatusViewModel::class.java
    }

    override fun getResourceLayout(): Int {
       return R.layout.fragment_status
    }

    override fun initView() {
        rcy_list_coin.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter= StatusAdapter(mutableListOf())
        }

        activity.findViewById<LinearLayout>(R.id.ln).visibility= View.VISIBLE
        activity.findViewById<Button>(R.id.btn_status).setBackgroundResource(R.drawable.ic_baseline_home_24_color)
    }


    override fun bindViewModel() {
        viewModel.getListStatus()
        viewModel.listStatus.observe(this, androidx.lifecycle.Observer {
            adapter.setLisst(it)
        })
    }
}