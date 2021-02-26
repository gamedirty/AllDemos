package com.sovnem.dpidrawable

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Scroller

class MainActivity : AppCompatActivity() {
    val channelId = "debug_notify"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(MyView(this))

        Runtime.getRuntime().exec("setprop debug.hwui.overdraw false")

//
//        findViewById<View>(R.id.fuck).setOnClickListener {
//            val notification: Notification =
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                    Notification.Builder(this)
//                        .setChannelId(channelId)
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentTitle("通知标题")
//                        .setContentText("通知内容")
//                        .build()
//                } else {
//                    TODO("VERSION.SDK_INT < O")
//                }
//            val notificationManager = this
//                .getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//            val channel =
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                    NotificationChannel(
//                        channelId,
//                        "通知的渠道名称",
//                        NotificationManager.IMPORTANCE_DEFAULT
//                    )
//                } else {
//                    TODO("VERSION.SDK_INT < O")
//                }
//            notificationManager.createNotificationChannel(channel)
//            notificationManager.notify(10, notification)
//        }
    }
}