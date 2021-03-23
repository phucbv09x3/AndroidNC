package com.example.androidnc.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Phuc on 15/1/2021
 */
enum class DateType(val type: String){
    OnlyDate("dd/MM/yyyy hh:mm:ss"),
    OnlyDate2("dd-MM-yyyy HH:mm:ss"),
}
object DateFormat {
    fun convertDate(date: Date = Date(),type: DateType): String{
        val sdf= SimpleDateFormat(type.type)
        return sdf.format(date)
    }
    fun convertDate(timeStamp: Long,type: DateType): String{
        val sdf= SimpleDateFormat(type.type)
        return sdf.format(timeStamp)
    }
    fun updateTimeStamp():Long{
        return System.currentTimeMillis()
    }

    fun formatUserName(type: DateType):SimpleDateFormat{
        return SimpleDateFormat(type.type)
    }
}