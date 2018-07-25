package com.techapp.james.broadcastdemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.techapp.james.broadcastdemo.dynamic.DynamicActivity
import com.techapp.james.broadcastdemo.staticReceiver.StaticActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dynamicBtn.setOnClickListener {
            var i = Intent(this, DynamicActivity::class.java)
            startActivity(i)
        }
        staticBtn.setOnClickListener {
            var i = Intent(this, StaticActivity::class.java)
            startActivity(i)
        }
    }
}
