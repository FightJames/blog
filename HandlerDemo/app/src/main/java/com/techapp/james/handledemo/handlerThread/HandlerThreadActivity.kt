package com.techapp.james.handledemo.handlerThread

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import com.techapp.james.handledemo.R
import kotlinx.android.synthetic.main.activity_loop_handler.*

class HandlerThreadActivity : AppCompatActivity() {
    lateinit var handlerThreadHandler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loop_handler)
//        HandlerThread整合了Thread、Looper和MessageQueue。
//啟動方式跟Thread相同。
        //開啟HandlerThread
        var handlerThread: HandlerThread = HandlerThread("HandlerThread")
        handlerThread.start()
        handlerThreadHandler = Handler(handlerThread.looper)
        var handler=Handler()
        downloadBtn.setOnClickListener {
           textView.text="Download..."
            handlerThreadHandler.post {
                Thread.sleep(10000)
               handler.post {
                   textView.text="Download success"
               }
            }
        }

    }
}
