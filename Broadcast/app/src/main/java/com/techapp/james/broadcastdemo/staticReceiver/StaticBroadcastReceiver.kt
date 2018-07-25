package com.techapp.james.broadcastdemo.staticReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class StaticBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, Thread.currentThread().toString(), Toast.LENGTH_LONG).show()
        //test receiver run main thread
        print(Thread.currentThread())
    }
}