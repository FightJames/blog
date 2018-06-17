package com.techapp.james.myapplication.activityTransitions

import android.app.ActivityOptions
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.techapp.james.myapplication.R
import kotlinx.android.synthetic.main.activity_first.*

class First_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        intentBtn.setOnClickListener {
            var activityOption = ActivityOptions.makeCustomAnimation(this, R.anim.abc_fade_in, R.anim.abc_fade_out)
            var i = Intent(this, SecondActivity::class.java)
//            this.startActivity(i, activityOption.toBundle())
            this.startActivity(i)
            overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom)
        }
    }
}
