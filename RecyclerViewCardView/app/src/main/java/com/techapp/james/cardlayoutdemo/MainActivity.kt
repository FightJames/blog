package com.techapp.james.cardlayoutdemo

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Adapter
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var adapter:RecyclerViewAdapter= RecyclerViewAdapter(7,this)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.adapter=adapter
    }
}
