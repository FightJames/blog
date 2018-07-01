package com.techapp.james.myapplication

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.util.Log

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("Start"," Foreground")
        var mBuilder = NotificationCompat.Builder(this, "MyNotifi")
        .setSmallIcon(R.drawable.notification_icon_background)
        .setContentTitle("ha")
        .setContentText("pp")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        startForeground(1,mBuilder.build())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        var thread = Thread(object : Runnable {
            override fun run() {
                var i = 0
                while (true) {
                    Log.d("hello", i.toString())
                    Thread.sleep(1000)
                    i++

                }
            }

        })
        thread.start()
        return super.onStartCommand(intent, flags, startId)
    }
}
