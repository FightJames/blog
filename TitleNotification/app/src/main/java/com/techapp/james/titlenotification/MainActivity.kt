package com.techapp.james.titlenotification

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.app.NotificationCompat
import android.content.Context.NOTIFICATION_SERVICE
import android.app.NotificationManager
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.Bitmap
import android.support.v4.content.ContextCompat
import android.graphics.drawable.Drawable
import android.app.NotificationChannel
import android.os.Build
import android.support.annotation.RequiresApi


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val mBuilder = NotificationCompat.Builder(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelLove = NotificationChannel(
                    "Channel ID",
                    "Channel Name",
                    NotificationManager.IMPORTANCE_HIGH)
            channelLove.description = "Hello People"
            channelLove.enableLights(true)
            channelLove.enableVibration(true)
            mNotificationManager.createNotificationChannel(channelLove)

            mBuilder.setContentTitle("Hello")
            mBuilder.setSmallIcon(R.drawable.ic_launcher_foreground)
            mBuilder.setAutoCancel(true)
            mBuilder.setContentInfo("Info")
            mBuilder.setTicker("狀態列")
//            mBuilder.setPriority(NotificationCompat.PRIORITY_MAX)

//            mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND)
            //設定震動方式，延遲0秒，震動1秒，延遲1秒、震動一秒
//            mBuilder.setVibrate(longArrayOf(0, 1000, 1000, 1000))
            mBuilder.setChannelId("Channel ID")
        } else {
            mBuilder.setContentTitle("Hello")
            mBuilder.setSmallIcon(R.drawable.ic_launcher_foreground)
            mBuilder.setAutoCancel(true)
            mBuilder.setContentInfo("Info")
            mBuilder.setTicker("狀態列")
            mBuilder.setPriority(NotificationCompat.PRIORITY_MAX)

            mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND)
            //設定震動方式，延遲0秒，震動1秒，延遲1秒、震動一秒
            mBuilder.setVibrate(longArrayOf(0, 1000, 1000, 1000))
        }
        button.setOnClickListener {
            mNotificationManager.notify(1, mBuilder.build())
        }
    }
}
