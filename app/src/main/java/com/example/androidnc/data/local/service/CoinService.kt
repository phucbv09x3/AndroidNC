package com.example.bitcoin.data.local.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.LocationManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import com.example.androidnc.R
import com.example.androidnc.data.local.room.CoinDatabase
import com.example.androidnc.data.local.room.CoinModelRoom
import com.example.androidnc.data.model.CoinModel
import com.example.androidnc.ui.list.ListCoinViewModel
import com.example.androidnc.ui.main.MainActivity
import com.example.androidnc.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * Created by Phuc on 20/1/2021
 */
class CoinService : Service() {
    val list = MutableLiveData<MutableList<CoinModelRoom>>()
    private lateinit var notificationManager: NotificationManager
    private lateinit var locationManager: LocationManager
    private lateinit var notificationLayoutCustom: RemoteViews
    private lateinit var notification: NotificationCompat.Builder
    private lateinit var db: CoinDatabase
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private val scope = CoroutineScope(Dispatchers.Main)
    lateinit var preferences: SharedPreferences

    companion object {
        const val CHANNEL_ID = "IDMapService"
        const val CHANNEL_NAME = "MapService"
        const val NOTIFICATION_ID = 100
        const val NOTIFICATION_TITLE = "Create by Miichisoft"
        const val ACTION_CLOSE = "CLOSE"
        const val DESCRIPTION = "YOUR_NOTIFICATION_CHANNEL_DESCRIPTION"
        const val TIME_DEFAULT = 60 * 60 * 1000L
        const val TEXT_NOTIFY_LOADING = "Đang cập nhật !"
        const val REQUEST_CODE = 1
        const val FLAGS_ZERO = 0
        const val MILLIS_ONE_MINUTE = 60 * 1000L
        const val NAME_FILE_SHARE = "data"
        const val KEY_SHARE = "key"
    }


    override fun onCreate() {
        super.onCreate()
        preferences = getSharedPreferences(NAME_FILE_SHARE, MODE_PRIVATE)
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationLayoutCustom = RemoteViews(packageName, R.layout.custom_notification)
        notification = NotificationCompat.Builder(this, CHANNEL_ID)
        registerNotificationChannel()
        createNotification()
        db = CoinDatabase.init(applicationContext)
        val intent = Intent(this, CoinService::class.java)
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        pendingIntent = PendingIntent.getService(
            this, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )

    }

    private fun requestAlarm(time: Long) {
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + time,
            time,
            pendingIntent
        )

    }

    private suspend fun insert(list: MutableList<CoinModel>) {
        val listInsert = list.map {
            CoinModelRoom(
                it.id, it.name, it.symbol, it.slug, it.quote.USD.price,
                ListCoinViewModel.VALUE_EMPTY
            )
        }.toMutableList()
        db.coiDAO().insertCoin(listInsert)
    }

    private fun update() {
        scope.launch {
            val listCoinLocal = db.coiDAO().getListCoinRoom() ?: mutableListOf()
            val listServer =
                Constants.apiCoinRepository(ListCoinViewModel.BASE_URL_COIN).getList().data
            val listCoinApi = listServer ?: mutableListOf()

            val listInsert = mutableListOf<CoinModel>()
            listCoinApi.forEach { server ->
                val findLocal = listCoinLocal.firstOrNull { local ->
                    server.id == local.id
                }
                findLocal?.let { model ->
                    val priceServer = server.quote.USD.price
                    val priceLocal = model.price
                    val diff = priceLocal != priceServer
                    if (diff) {
                        model.isCheckPrice =
                            if (priceServer > priceLocal) ListCoinViewModel.VALUE_UP else ListCoinViewModel.VALUE_DOWN
                        model.price = priceServer
                        model.isNeedUpdate = true
                    }
                } ?: kotlin.run {
                    listInsert.add(server)
                }
            }
            val listUpdate = listCoinLocal.filter {
                it.isNeedUpdate
            }
            if (listUpdate.isNotEmpty()) {
                list.value = listUpdate.toMutableList()
                db.coiDAO().updateListChange(listUpdate.toMutableList())
            }
            if (listInsert.isNotEmpty()) {
                insert(listInsert)
            }
            var text = ""
            listUpdate.forEach {
                text += it.name
            }

            if (text.isNotEmpty()) {
                notificationLayoutCustom.setTextViewText(
                    R.id.tv_notification_user,
                    text
                )
            } else {
                notificationLayoutCustom.setTextViewText(
                    R.id.tv_notification_user,
                    TEXT_NOTIFY_LOADING
                )

            }
            notificationManager.notify(NOTIFICATION_ID, notification.build())
        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("onStartCommand", "${System.currentTimeMillis()}")
        val value = preferences.getString(KEY_SHARE, "")
        if (value?.isEmpty() == true) {
            requestAlarm(TIME_DEFAULT)
        } else {
            requestAlarm(value!!.toLong() * MILLIS_ONE_MINUTE)
            update()
        }
        when (intent?.action) {
            ACTION_CLOSE -> {
                preferences.edit().putString(KEY_SHARE, "").apply()
                stopSelf()
                scope.cancel()
                alarmManager.cancel(pendingIntent)
                notificationManager.cancel(NOTIFICATION_ID)
            }
        }
        return START_NOT_STICKY
    }

    private fun registerNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = DESCRIPTION
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification() {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE, intent, FLAGS_ZERO)

        val intentCoinService = Intent(this, CoinService::class.java).setAction(ACTION_CLOSE)
        val pendingIntentService =
            PendingIntent.getService(this, REQUEST_CODE, intentCoinService, FLAGS_ZERO)
        notificationLayoutCustom.setImageViewResource(
            R.id.img_notification,
            R.drawable.icon_coin_test
        )
        notificationLayoutCustom.setTextViewText(R.id.tv_title, NOTIFICATION_TITLE)
        notificationLayoutCustom.setTextViewText(R.id.tv_notification_user, TEXT_NOTIFY_LOADING)
        notificationLayoutCustom.setOnClickPendingIntent(R.id.btn_closeNoti, pendingIntentService)

        notification.setContentIntent(pendingIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setCustomContentView(notificationLayoutCustom)
            .setSmallIcon(R.drawable.icon_coin)
            .setPriority(NotificationCompat.PRIORITY_LOW)

        startForeground(NOTIFICATION_ID, notification.build())

    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}