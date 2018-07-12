package com.techapp.james.iguidemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.techapp.james.iguidemo.adapter.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.nio.charset.MalformedInputException

class MainActivity : AppCompatActivity() {
    var data: ArrayList<Any>? = null
    var mainAdapter: MainAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        data = ArrayList<Any>()
        data!!.add("1")
        data!!.add("1")
        data!!.add("1")
        data!!.add("1")
        data!!.add("1")
        allDataRecyclerView.layoutManager = LinearLayoutManager(this)
        mainAdapter = MainAdapter(data!!, this)
        allDataRecyclerView.adapter = mainAdapter
        allDataRecyclerView.layoutManager.scrollToPosition(0)
        refresh_layout.setOnRefreshListener {
            refresh_layout.isRefreshing = false
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

    }
}
