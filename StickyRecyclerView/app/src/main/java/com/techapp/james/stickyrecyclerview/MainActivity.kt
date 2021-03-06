package com.techapp.james.stickyrecyclerview

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.techapp.james.stickyrecyclerview.dataStructure.ConcreteData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val title = "abcdefghijklmnopqrst"
        val item = "50412789458263"

        Log.d("Adapter", title.toMutableList().shuffled().joinToString(","));

        title.forEach { t ->
            item.forEach { i ->
                recyclerListVertical.insertOrUpdate(t.toString(), i.toString())
//                recyclerListHorizon.insertOrUpdate(t.toString(), i.toString())
            }
        }
    }
}
