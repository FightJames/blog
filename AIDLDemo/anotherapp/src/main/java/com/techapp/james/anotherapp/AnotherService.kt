package com.techapp.james.anotherapp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Process
import android.util.Log

class AnotherService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("pass", "hello")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        return object : IMyAidlInterface.Stub() {
            override fun getPid(): String {
                return Process.myPid().toString()
            }

        }
    }
}
