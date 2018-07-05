package com.techapp.james.service.normalService

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("Service", "Do something startId  " + startId)
        //return super.onStartCommand(intent, flags, startId)
        var i = Intent()
        i.action = NormalServiceActivity.SERVICECHANGE
        i.putExtra("data",1)
        sendBroadcast(i)
        return START_STICKY
    }

    /**
    START_STICKY : Service can be killed , system will reconstruct it , but Intent will be null。
    START_NOT_STICKY : Service can be killed, but system won't reconstruct it.
    START_REDELIVER_INTENT : Service can be killed , system will reconstruct it and repass intent to service.
    These paramter you can return it in onStartCommand.You can use reconstruct function.
     **/
    override fun onDestroy() {
        super.onDestroy()
        Log.d("Service", "OnDestroy")
    }
}
