package com.example.androidnc.ui.list

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidnc.R
import com.example.androidnc.databinding.FragmentListCoinBinding
import com.example.bitcoin.data.local.service.CoinService
import com.example.androidnc.ui.about.ListCoinAdapter
import com.example.androidnc.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_list_coin.*

/**
 * Created by Phuc on 20/1/2021
 */
class ListCoinFragment : BaseFragment<ListCoinViewModel, FragmentListCoinBinding>(),
    View.OnClickListener {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editSharedPreferences: SharedPreferences.Editor

    companion object {
        const val KEY_SETTING_TIME = "KEY_SETTING_TIME"
        const val BUTTON_ACCESS_DIALOG = "Xác nhận"
        const val BUTTON_CANCEL_DIALOG = "Hủy"
    }


    override fun createViewModel(): Class<ListCoinViewModel> {
        return ListCoinViewModel::class.java
    }

    override fun getResourceLayout(): Int {
        return R.layout.fragment_list_coin
    }

    override fun initView() {

    }

    override fun bindViewModel() {
        viewModel.getListCoin()
        viewModel.listCoin.observe(this, {
            (rcy_list_coin.adapter as ListCoinAdapter).setList(it)
        })
    }

    override fun onClick(v: View?) {

    }

}