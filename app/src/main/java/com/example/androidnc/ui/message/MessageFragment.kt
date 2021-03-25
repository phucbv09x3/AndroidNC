package com.example.androidnc.ui.message

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.example.androidnc.R
import com.example.androidnc.databinding.FragmentMessageBinding
import com.example.androidnc.ui.base.BaseFragment


class MessageFragment : BaseFragment<MessageViewModel,FragmentMessageBinding>() {
    override fun createViewModel(): Class<MessageViewModel> {
        return MessageViewModel::class.java
    }

    override fun getResourceLayout(): Int {
        return R.layout.fragment_message
    }

    override fun initView() {
        activity.findViewById<LinearLayout>(R.id.ln).visibility= View.VISIBLE
        activity.findViewById<Button>(R.id.btn_message).setBackgroundResource(R.drawable.ic_baseline_message_24_color)
    }

    override fun bindViewModel() {
    }
}