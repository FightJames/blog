package com.techapp.james.service.bindService

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.techapp.james.service.R
import kotlinx.android.synthetic.main.activity_bind_service.*

class BindServiceActivity : AppCompatActivity() {

    var myBinder: MyBindService.MyBind? = null
    var serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d("BindServiceActivity", " onServiceDisconnected" + name.toString())
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d("BindServiceActivity", " onServiceConnected")
            myBinder = service as MyBindService.MyBind
            textView.text = myBinder!!.doSomething()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bind_service)
        bindServiceBtn.setOnClickListener {
            var i = Intent(this, MyBindService::class.java)
            bindService(i, serviceConnection, Context.BIND_AUTO_CREATE)
        }
        unBindBtn.setOnClickListener {
            unbindService(serviceConnection)
        }
    }
}
