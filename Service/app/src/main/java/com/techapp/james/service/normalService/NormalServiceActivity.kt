package com.techapp.james.service.normalService

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.techapp.james.service.R
import kotlinx.android.synthetic.main.activity_normal_service.*

class NormalServiceActivity : AppCompatActivity() {
    companion object {
        const val SERVICECHANGE = "SERVICECHANGE"
    }

    var receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            dataTextView.text = intent?.getIntExtra("data", 11)?.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal_service)
        registReceiver()
        startServiceBtn.setOnClickListener {
            var i = Intent(this, MyService::class.java)
            startService(i)
        }
    }

    fun registReceiver() {
        var intentFilter = IntentFilter()
        intentFilter.addAction(SERVICECHANGE)
        registerReceiver(receiver, intentFilter)
    }
}
