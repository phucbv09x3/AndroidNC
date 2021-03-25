package com.example.androidnc.ui.login

import android.app.Application
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.androidnc.R
import com.example.androidnc.ui.base.BaseViewModel
import com.example.androidnc.ui.list.ListCoinFragment
import com.example.androidnc.ui.signup.SignUpFragment
import com.example.androidnc.utils.Constants
import com.example.androidnc.utils.DateFormat
import com.example.androidnc.utils.DateType
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created by Phuc on 15/1/2021
 */
class LoginAccViewModel() : BaseViewModel() {
    companion object {
        var patternMail =
            "^[_A-Za-z0-9-]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$"
        var patternPassword = "[a-zA-Z0-9]+[@#$%^&*]+[a-zA-A-z0-9]+"
    }

    val userName = ObservableField("")
    val password = ObservableField("")
    private var auth = FirebaseAuth.getInstance()

    val valueCheckLogin = MutableLiveData<Int>()
    private val current: FirebaseUser? = auth.currentUser

    fun onClickLogin() {
        val name = userName.get().toString()
        val pass = password.get().toString()
        if (name.isNotEmpty() && pass.matches(patternPassword.toRegex())
            && pass.isNotEmpty() && name.matches(patternMail.toRegex())
        ) {
            auth.signInWithEmailAndPassword(name, pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        //updateUI(current)
                        if (current?.isEmailVerified == true) {
                            valueCheckLogin.value = 1
                        }else{
                            valueCheckLogin.value=4
                        }
                    } else {
                        valueCheckLogin.value = 2
                    }
                }
        } else if (!name.matches(patternMail.toRegex())) {
            valueCheckLogin.value = 3
        }
    }

    fun clickSignUp(){
        navigation.switchFragment(SignUpFragment::class)
    }

    private fun resetText() {
        userName.set("")
        password.set("")
    }
}