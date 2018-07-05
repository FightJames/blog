package com.techapp.james.service.bindService

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MyBindService : Service() {

    override fun onCreate() {
        Log.d("Service", "onCreate")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("Service", "onStart")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.d("Service", "onDestroy")
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder {
        // TODO: Return the communication channel to the service.
        Log.d("Service", "onBind")
        return MyBind()
    }

    class MyBind : Binder {
        constructor() {
            Log.d("MyBind", "Create MyBind")
        }

        fun doSomething() :String{
            Log.d("MyBind", "DoSomething")
            return "I'm Binder."
        }
    }
}
