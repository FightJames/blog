package com.techapp.james.broadcastdemo

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var br = MyBroadcastRecevier()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //register Receiver
        var iFilter = IntentFilter()
        iFilter.addAction(Config.flag)
//        this.registerReceiver(br, iFilter)
        LocalBroadcastManager.getInstance(applicationContext).registerReceiver(br, iFilter)
//To register for local broadcasts, call LocalBroadcastManager.registerReceiver(BroadcastReceiver, IntentFilter) instead.
        sendBtn.setOnClickListener {
            var i = Intent()
            i.setAction(Config.flag)
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(i)
        }
    }
}
