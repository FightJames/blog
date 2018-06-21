package com.techapp.james.recycleviewcustomer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.techapp.james.recycleviewcustomer.customRecycler.RecyclerActivity
import com.techapp.james.recycleviewcustomer.speedRecycler.SpeedRecyclerActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        customBtn.setOnClickListener {
            var i = Intent(this, RecyclerActivity::class.java)
            startActivity(i)
        }
        speedBtn.setOnClickListener {
            var i = Intent(this, SpeedRecyclerActivity::class.java)
            startActivity(i)
        }
    }
}
