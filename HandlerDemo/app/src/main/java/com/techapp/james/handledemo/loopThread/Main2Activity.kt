package com.techapp.james.handledemo.loopThread

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.techapp.james.handledemo.R
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {
    var mHandler: Handler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        mHandler = object : Handler() {
            override fun handleMessage(msg: Message?) {
                test.text = msg!!.what.toString()

                Handlers.loopThreadHanlder!!.postDelayed(object : Runnable {
                    override fun run() {
                        System.out.println("LoopThreadHandler")
                        Handlers.loopThreadHanlder!!.postDelayed(this, 1000)
                    }
                }, 1000)
            }
        }
        var thread = LoopThread(mHandler!!)
        thread.start()


        loopThreadBtn.setOnClickListener {
            thread.stopThread()
        }
    }
}
