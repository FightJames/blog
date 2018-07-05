package com.techapp.james.service.intentService

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.techapp.james.service.R
import kotlinx.android.synthetic.main.activity_intent_service.*

class IntentServiceActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_intent_service)
        registerReceiver()
        button.setOnClickListener {
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
        MyIntentService.stopService(this)
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
