package com.techapp.james.service.intentService

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.util.Log

class MyIntentService : IntentService("MyIntentService") {

    companion object {
        val SERVICE_ACTION = "DownloadImage"
        val EXTRA_PATH = "Image"
        fun startDownloadImage(context: Context, path: String) {
            var i = Intent(context, MyIntentService::class.java)
            i.setAction(SERVICE_ACTION)
            i.putExtra(EXTRA_PATH, path)
            context.startService(i)
        }

        fun stopService(context: Context) {
            var i = Intent(context, MyIntentService::class.java)
            context.stopService(i)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("IntentServiceId", " " + startId)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            SERVICE_ACTION -> {
                val path = intent.getStringExtra(EXTRA_PATH)
                handleDownloadImage(path)
            }
        }
    }


    fun handleDownloadImage(path: String) {
        try {
            Thread.sleep(2000)
            var i = Intent(IntentServiceActivity.DOWNLOAD_RESULT)
            i.putExtra(EXTRA_PATH, path)
            sendBroadcast(i)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("IntentService", " onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("IntentService", " onDestroy")
    }
}
