package com.techapp.james.gson.test

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.techapp.james.gson.R
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        var target = Gson().fromJson(FackJSON.JSONData, Target::class.java)
        textView2.text = target.first_name + " " + target.status + " " + target.last_name+" "+target.inS.k

    }
}
