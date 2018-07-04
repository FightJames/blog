package com.techapp.james.recycleviewcustomer.addRemoveItem

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.techapp.james.recycleviewcustomer.R
import kotlinx.android.synthetic.main.activity_add_remove.*

class AddRemoveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_remove)
        list.layoutManager = LinearLayoutManager(this)
        var adapter = AddRemoveAdapter(this)
        list.adapter = adapter
        addBtn.setOnClickListener {
            adapter.size++
            adapter.notifyItemInserted(adapter.size)
        }
    }
}
