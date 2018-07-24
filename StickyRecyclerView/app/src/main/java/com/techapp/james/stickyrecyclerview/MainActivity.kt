package com.techapp.james.stickyrecyclerview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val data: Data = Data()
        val title = "abcdefghijklmnopqrst"
        val item = "5041278963"

        Log.d("Adapter", title.toMutableList().shuffled().joinToString(","));

        title.forEach { t -> item.forEach { i -> data.insertOrUpdate(t.toString(), i.toString()) } }
        var myAdapter = MyAdapter(data)
        recyclerList.adapter = myAdapter
        recyclerList.layoutManager = LinearLayoutManager(this)
        recyclerList.addItemDecoration(ItemDecoration(data))

        recyclerList.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }
}
