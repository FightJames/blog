package com.techapp.james.gson

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var gson = GsonBuilder()
                .registerTypeAdapter(Family::class.java, ConvertFamily())
                .create()
        var family = gson.fromJson(FackJSON.JSONData, Family::class.java)
        var result = family.father + "\n" + family.son + "\n" + family.mother
        textView.text = result
    }
}
