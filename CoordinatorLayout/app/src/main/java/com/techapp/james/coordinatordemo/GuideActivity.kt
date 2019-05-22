package com.techapp.james.coordinatordemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_guide.*

class GuideActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)
        notToolBar.setOnClickListener {
            var i = Intent(this@GuideActivity, NonToolbarActivity::class.java)
            startActivity(i)
        }
        toolBarBtn.setOnClickListener {
            var i = Intent(this@GuideActivity, ToolbarActivity::class.java)
            startActivity(i)
        }
    }
}
