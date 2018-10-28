package com.techapp.james.handledemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.techapp.james.handledemo.handlerThread.HandlerThreadActivity
import com.techapp.james.handledemo.loopThread.Main2Activity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loopThreadBtn.setOnClickListener {
            var i = Intent(this, Main2Activity::class.java)
            startActivity(i)
        }
        loopHandlerBtn.setOnClickListener {
            var i = Intent(this, HandlerThreadActivity::class.java)
            startActivity(i)
        }
    }
}
