package com.techapp.james.intentservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        public val DOWNLOAD_RESULT = "Complete"
    }

    private val downloadReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                if (intent.action.equals(DOWNLOAD_RESULT)) {
                    var path = intent.getStringExtra(MyIntentService.EXTRA_PATH)
                    handleResult(path)
                }
            }
        }
    }

    fun handleResult(path: String) {
        var tv = textViewRoot.findViewWithTag<TextView>(path)
        tv.text = path + " download success ~~ "
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerReceiver()
        newBtn.setOnClickListener {
            addTask()
        }
    }

    fun registerReceiver() {
        var filter = IntentFilter()
        filter.addAction(DOWNLOAD_RESULT)
        registerReceiver(downloadReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(downloadReceiver)
    }

    var i = 0
    fun addTask() {
        var path = "http://james/imags/" + (++i) + ".jpg"
        MyIntentService.startDownloadImage(this, path)
        var textView = TextView(this)
        textViewRoot.addView(textView)
        textView.text = path + " is download ..."
        textView.tag = path
    }
}
