package com.techapp.james.slideimagedemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.techapp.james.slideimagedemo.padding.ViewPagerActivity
import com.techapp.james.slideimagedemo.scrollSpeed.ViewPagerScrollActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        paddingBtn.setOnClickListener {
            var i = Intent(this, ViewPagerActivity::class.java)
            startActivity(i)
        }
        scrollBtn.setOnClickListener {
            var i = Intent(this, ViewPagerScrollActivity::class.java)
            startActivity(i)
        }
    }
}
