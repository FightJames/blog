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


        val test: Test = Test()
        val title = "abcdefghijklmnopqrst"
        val item = "5041278963"

        Log.d("Adapter", title.toMutableList().shuffled().joinToString(","));

        title.forEach { t -> item.forEach { i -> test.insertOrUpdate(t.toString(), i.toString()) } }
        var myAdapter = MyAdapter(test)
        recyclerList.adapter = myAdapter
        recyclerList.layoutManager = LinearLayoutManager(this)
        recyclerList.addItemDecoration(ItemDecoration(test))

        recyclerList.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }
}
