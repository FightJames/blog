package com.techapp.james.servicedemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var i = Intent(this, MyService::class.java)
        startService(i)
    }

    override fun onRestart() {
        super.onRestart()
        var i = Intent(this, MyService::class.java)
        startService(i)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, MyService::class.java))
    }
}
