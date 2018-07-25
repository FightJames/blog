package com.techapp.james.broadcastdemo.staticReceiver

import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import com.techapp.james.broadcastdemo.R
import kotlinx.android.synthetic.main.activity_static.*

class StaticActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_static)
        sendBtn.setOnClickListener {
            var i = Intent()
            i.setAction("Hello")
            sendBroadcast(i)
        }
    }
}
