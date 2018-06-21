package com.techapp.james.recycleviewcustomer.customRecycler

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.techapp.james.recycleviewcustomer.R

import kotlinx.android.synthetic.main.activity_recycler.*


class RecyclerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)
        var dataList = ArrayList<String>()
        dataList.add("1")
        dataList.add("2")
        dataList.add("3")
        dataList.add("4")
        dataList.add("5")
        dataList.add("6")
        dataList.add("7")
        dataList.add("8")
        dataList.add("9")

        var adapter: MyAdapter = MyAdapter(this, dataList)

        recyclerView.layoutManager = LinearLayoutManager(this)
        //recyclerView.layoutManager=GridLayoutManager(this,3)
        recyclerView.adapter = adapter
       // recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST))
    }
}
