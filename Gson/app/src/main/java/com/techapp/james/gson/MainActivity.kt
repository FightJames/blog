package com.techapp.james.gson

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.gson.GsonBuilder
import com.techapp.james.gson.test.Main2Activity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            var i = Intent(this, Main2Activity::class.java)
            startActivity(i)
        }
        var gson = GsonBuilder()
                .registerTypeAdapter(Family::class.java, ConvertFamily())
                .create()
        var family = gson.fromJson(FackJSON.JSONData, Family::class.java)
        var result = family.father + "\n" + family.son + "\n" + family.mother
        textView.text = result

    }
}
