package com.techapp.james.musicdemo.model.threadModel

import android.os.Handler
import android.os.Message


/**
 * Created by James on 2018/3/27.
 */
object NullHandler : Handler() {
    override fun sendMessageAtTime(msg: Message?, uptimeMillis: Long): Boolean {
        return super.sendMessageAtTime(msg, uptimeMillis)
    }

    override fun dispatchMessage(msg: Message?) {
    }

    override fun handleMessage(msg: Message?) {
    }

    override fun getMessageName(message: Message?): String {
        return super.getMessageName(message)
    }

    override fun toString(): String {
        return super.toString()
    }
}