package com.techapp.james.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Process
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var textView = findViewById<TextView>(R.id.textView)
        textView.text = "James"
        var i = Intent(this, MyService::class.java)
        startService(i)
    }

    override fun onDestroy() {
        var i = Intent(this, MyService::class.java)
        stopService(i)
        super.onDestroy()
    }
}
