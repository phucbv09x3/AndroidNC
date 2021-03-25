package com.example.androidnc.ui.login


import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.androidnc.R
import com.example.androidnc.databinding.FragmentLoginCoinBinding
import com.example.androidnc.ui.base.BaseFragment
import com.example.androidnc.ui.status.StatusFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * Created by Phuc on 15/1/2021
 */

class LoginAccFragment : BaseFragment<LoginAccViewModel, FragmentLoginCoinBinding>() {
    override fun createViewModel(): Class<LoginAccViewModel> {
        return LoginAccViewModel::class.java
    }

    override fun getResourceLayout(): Int {
        return R.layout.fragment_login_coin
    }

    override fun initView() {
        activity.findViewById<LinearLayout>(R.id.ln).visibility= View.GONE

        val user=FirebaseAuth.getInstance().currentUser
        updateUI(user)
    }

    fun updateUI(current:FirebaseUser?){
        current?.let {
            if (current.isEmailVerified){
                navigators.switchFragment(StatusFragment::class)
            }
        }
    }

    override fun bindViewModel() {
        viewModel.onClickLogin()
        viewModel.valueCheckLogin.observe(this, Observer {
            when(it){
                1->{
                    Toast.makeText(context, "ok", Toast.LENGTH_LONG).show()
                    navigators.switchFragment(StatusFragment::class)
                }
                2->{

                }
                3->{

                }
                4->{
                    Toast.makeText(context, "Please verify your email", Toast.LENGTH_LONG).show()

                }
            }
        })
    }
}