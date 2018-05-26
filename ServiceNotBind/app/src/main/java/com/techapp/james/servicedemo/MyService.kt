package com.techapp.james.servicedemo

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

/**
 * Created by James on 2018/3/19.
 */
class MyService : Service {
    constructor() {
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("Service", "Do something startId  " + startId)
        //return super.onStartCommand(intent, flags, startId)
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