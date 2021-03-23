package com.example.androidnc.utils

import android.util.Log
import androidx.databinding.library.BuildConfig

/**
 * Created by Phuc on 15/1/2021
 */

inline fun printLog(message: Any?) {
    if (message == null || !BuildConfig.DEBUG) return
    val stackTraceElement = Thread.currentThread().stackTrace[4]
    Log.d(
        "[Miichi - ${stackTraceElement.fileName}->${stackTraceElement.methodName}->${stackTraceElement.lineNumber}]",
        "#$message"
    )
}