package com.techapp.james.handledemo

import android.os.Handler
import android.os.Looper
import android.os.Message

/**
 * Created by James on 2018/3/17.
 */
class LoopThread : Thread {
    var mHandler: Handler


    constructor(mHandler: Handler) {
        this.mHandler = mHandler
    }

    override fun run() {
        Looper.prepare()
        var i = 0
        var msg = Message()
        msg.what = i
        mHandler.sendMessage(msg)

        Looper.loop() //Pass要等到looper終止後才會被執行
        println("Pass")
    }

    fun stopThread() {
        this.interrupt()
    }

}