package com.techapp.james.aidldemo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.Process
import android.util.Log
import android.widget.Toast
import com.techapp.james.anotherapp.IMyAidlInterface
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var anotherBinder: IMyAidlInterface
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myPidText.text = Process.myPid().toString()
        var i = Intent()
        i.component = ComponentName("com.techapp.james.anotherapp", "com.techapp.james.anotherapp.AnotherService")
        bindService(i, object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder) {
//                anotherBinder = service as IMyAidlInterface
//                Toast.makeText(this@MainActivity, "AnotherPid $anotherBinder.pid", Toast.LENGTH_LONG)
                anotherBinder = IMyAidlInterface.Stub.asInterface(service)
                Log.d("pass ", "Bind  ${anotherBinder.pid}")
            }

        }, Context.BIND_AUTO_CREATE)
        anotherAppBtn.setOnClickListener {
            //            Toast.makeText(this, "AnotherPid $anotherBinder.pid", Toast.LENGTH_LONG)
            Toast.makeText(this@MainActivity, "AnotherPid ${anotherBinder.pid}", Toast.LENGTH_LONG).show()
        }
    }
}
