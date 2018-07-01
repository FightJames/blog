package com.techapp.james.intentservice

import android.app.IntentService
import android.content.Context
import android.content.Intent
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
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("IntentServiceId", " " + startId)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleIntent(intent: Intent?) {
        intent?.let {
            val action = intent.action
            if (SERVICE_ACTION.equals(action)) {
                val path = intent.getStringExtra(EXTRA_PATH)
                handleDownloadImage(path)
            }
        }
    }

    fun handleDownloadImage(path: String) {
        try {
            Thread.sleep(2000)
            var ii=0
            while(true){
                Log.d("ii ",ii.toString())
                Thread.sleep(2000)
                ii++
            }
            var i = Intent(MainActivity.DOWNLOAD_RESULT)
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
