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




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            val drawable = ContextCompat.getDrawable(this, R.mipmap.ic_launcher_image)
            val bitmap = (drawable as BitmapDrawable).bitmap

            val mBuilder = NotificationCompat.Builder(this)
            mBuilder.setContentTitle("Hello")
            mBuilder.setSmallIcon(R.drawable.ic_launcher_foreground)
            mBuilder.setLargeIcon(bitmap)
            mBuilder.setAutoCancel(true)
            mBuilder.setContentInfo("Info")
            mBuilder.setTicker("狀態列")
            mBuilder.setPriority(NotificationCompat.PRIORITY_MAX)

            mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND)
            //設定震動方式，延遲0秒，震動1秒，延遲1秒、震動一秒
            mBuilder.setVibrate(longArrayOf(0, 1000, 1000, 1000))
            val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            mNotificationManager.notify(1, mBuilder.build())
        }
    }
}
