package com.techapp.james.stickyrecyclerview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var myAdapter = MyAdapter()
        recyclerList.adapter = myAdapter
        recyclerList.layoutManager = LinearLayoutManager(this)
        recyclerList.addItemDecoration(ItemDecoration(myAdapter.test.titles))

        recyclerList.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }
}
