package com.techapp.james.myapplication.activityTransitions

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.techapp.james.myapplication.R

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom)
    }
}
