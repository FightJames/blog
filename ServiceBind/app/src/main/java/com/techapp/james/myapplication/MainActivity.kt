package com.techapp.james.myapplication

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var myBinder: MyBindService.MyBind? = null
    var serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d("MainActivity", "onServiceDisconnected" + name.toString())
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d("MainActivity", "onServiceConnected")
            myBinder = service as MyBindService.MyBind
            myBinder!!.doSomething()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var i = Intent(this, MyBindService::class.java)
        bindService(i, serviceConnection, Context.BIND_AUTO_CREATE)

        unbindBtn.setOnClickListener {
            unbindService(serviceConnection)
        }
    }
}
