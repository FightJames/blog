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
        var list = ArrayList<String>()
        //40
        for (i in 0..3) {
            list.add("台灣")
            list.add("台南")
            list.add("高雄")
            list.add("台北")
        }

        for (i in 0..10) {
            list.add("台灣")
            list.add("台南")
            list.add("高雄")
        }
        recyclerList.adapter = MyAdapter(list)
        recyclerList.layoutManager = LinearLayoutManager(this)
        recyclerList.addItemDecoration(ItemDecoration())
        recyclerList.addItemDecoration(DividerItemDecoration(this,LinearLayoutManager.VERTICAL))
    }
}
