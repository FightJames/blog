package com.techapp.james.broadcastdemo.staticReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class StaticBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        //test receiver run main thread
        Toast.makeText(context, Thread.currentThread().toString(), Toast.LENGTH_LONG).show()
    }
}