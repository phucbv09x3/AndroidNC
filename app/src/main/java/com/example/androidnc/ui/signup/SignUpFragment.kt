package com.example.androidnc.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.androidnc.R
import com.example.androidnc.databinding.FragmentSingUpBinding
import com.example.androidnc.ui.base.BaseFragment
import com.example.androidnc.ui.login.LoginAccFragment


class SignUpFragment :BaseFragment<SignUpViewModel,FragmentSingUpBinding>(){
    override fun createViewModel(): Class<SignUpViewModel> {
        return SignUpViewModel::class.java
    }

    override fun getResourceLayout(): Int {
        return R.layout.fragment_sing_up
    }

    override fun initView() {
    }

    override fun bindViewModel() {
        viewModel.register()
        viewModel.valueCheckSignIn.observe(this, Observer {
            when(it){
                1->{
                    val bundle = arguments
                    bundle!!.putString("k","")
                    navigators.switchFragment(LoginAccFragment::class)
                }
                2->{
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                3->{
                    Toast.makeText(
                        context, "Sai cu phap !.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}