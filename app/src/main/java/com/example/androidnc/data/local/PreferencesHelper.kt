package com.example.androidnc.data.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import java.io.Serializable
import javax.inject.Inject

/**
 * Created by Phuc on 15/1/2021
 */
class PreferencesHelper @Inject constructor(context: Context, prefFileName: String) :
    IPreferenceHelper, Serializable {

    companion object {
        private const val MASTER_KEY_ALIAS = "Base_ALIAS"
        private const val KEY_SIZE = 256
        const val TOKEN_KEY = "token"
        const val USERNAME = "username"
        const val PASSWORD = "password"
        const val EMAIL = "email"
        const val ACCOUNT = "account"
        val gson = Gson()
    }

    private var masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private var sharedPreferences = EncryptedSharedPreferences.create(
        prefFileName,
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override var token: String
        get() = sharedPreferences.getString(TOKEN_KEY, "")!!
        set(value) {
            sharedPreferences.edit().apply {
                putString(TOKEN_KEY, value)
            }.apply()
        }
}