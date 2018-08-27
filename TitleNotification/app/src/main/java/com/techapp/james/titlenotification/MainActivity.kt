package com.techapp.james.titlenotification

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.app.NotificationCompat
import android.app.NotificationManager
import android.content.Context
import android.app.NotificationChannel
import android.os.Build


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                    "Channel ID",
                    "Channel Name",
                    NotificationManager.IMPORTANCE_HIGH)


//            Returns the user visible description of this channel.
            channel.description = "Hello"
            channel.enableLights(true)
//            Sets whether notifications posted to this channel should display notification lights, on devices that support that feature.
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(0, 1000, 1000, 1000)
            mNotificationManager.createNotificationChannel(channel)

            val mBuilder = NotificationCompat.Builder(this, "Channel ID")

            mBuilder.setContentTitle("Hello")
            mBuilder.setSmallIcon(R.drawable.ic_launcher_foreground)
            mBuilder.setAutoCancel(true)
            mBuilder.setContentInfo("Info")
            mBuilder.setTicker("狀態列")



            button.setOnClickListener {
                mNotificationManager.notify(1, mBuilder.build())
            }
        } else {

            val mBuilder = NotificationCompat.Builder(this)
            mBuilder.setContentTitle("Hello")
            mBuilder.setSmallIcon(R.drawable.ic_launcher_foreground)
            mBuilder.setAutoCancel(true)
            mBuilder.setContentInfo("Info")
//            Set this flag if you would only like the sound, vibrate and ticker to be played if the notification is not already showing.
            mBuilder.setTicker("狀態列")
            mBuilder.setPriority(NotificationCompat.PRIORITY_MAX)
//mBuilder.setLargeIcon()
            mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND)
            //設定震動方式，延遲0秒，震動1秒，延遲1秒、震動一秒
            mBuilder.setVibrate(longArrayOf(0, 1000, 1000, 1000))

            button.setOnClickListener {
                mNotificationManager.notify(1, mBuilder.build())
            }
        }
    }
}
