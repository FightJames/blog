package com.techapp.james.coordinatordemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_toolbar.*

class ToolbarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyAdapter(this)
        titleBar.visibility=View.VISIBLE
        titleBar.post {
            cToolbar.minimumHeight = titleBar.measuredHeight + 10
        }
        appbar_layout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
        }
    }

}
