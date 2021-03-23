package com.example.androidnc.ui.signup

import android.database.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.androidnc.ui.base.BaseViewModel
import com.example.androidnc.ui.login.LoginAccFragment
import com.example.androidnc.ui.login.LoginAccViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpViewModel : BaseViewModel() {
    var fullName = ObservableField("")
    var email = ObservableField("")
    var password = ObservableField("")
    var valueCheckSignIn = MutableLiveData<Int>()
    private var auth = FirebaseAuth.getInstance()
    private var firebaseReference: DatabaseReference? = null

    fun clickSignIn(){
        navigation.switchFragment(LoginAccFragment::class)
    }

    fun register() {
        val name = fullName.get().toString()
        val mail = email.get().toString()
        val pass = password.get().toString()
        if (pass.isNotEmpty() && pass.matches(LoginAccViewModel.patternPassword.toRegex())
            && mail.isNotEmpty() &&mail.matches(LoginAccViewModel.patternMail.toRegex())
            && name.isNotEmpty()
        ) {
            auth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        val currentUser = auth.currentUser
                        val email = currentUser.email
                        val userID = currentUser.uid
                        firebaseReference = FirebaseDatabase.getInstance().getReference("Account")
                        val hashMap = HashMap<String, String>()
                        hashMap["name"] = name
                        hashMap["email"] = email
                        hashMap["password"] = pass
                        hashMap["uid"] = userID
                        firebaseReference?.child(userID)?.setValue(hashMap)

                        currentUser.sendEmailVerification()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    valueCheckSignIn.value = 1
                                }
                            }
                    } else {
                        valueCheckSignIn.value = 2
                    }

                }
        } else {
            valueCheckSignIn.value = 3
        }
    }

}