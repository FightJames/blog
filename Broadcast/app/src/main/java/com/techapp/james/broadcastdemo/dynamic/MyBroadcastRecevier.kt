package com.techapp.james.broadcastdemo.dynamic

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MyBroadcastRecevier : BroadcastReceiver() {
    companion object {
        private val TAG = "MyBroadcastRecevier"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        var string: StringBuilder = StringBuilder()
        string.append("Action " + intent!!.action + " ")
        string.append("URI " + intent!!.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n")
        var log = string.toString()
        Log.d("TAG", log)
        Toast.makeText(context!!, log, Toast.LENGTH_LONG).show()
    }
}