package com.techapp.james.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.techapp.james.myapplication.activityTransitions.First_Activity
import com.techapp.james.myapplication.frameAnimation.FrameActivity
import com.techapp.james.myapplication.shareElements.ShareOneActivity
import com.techapp.james.myapplication.tweenAnimation.TweenActivity
import kotlinx.android.synthetic.main.activity_select.*

class SelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)
        frameBtn.setOnClickListener {
            var i = Intent(this, FrameActivity::class.java)
            startActivity(i)
        }
        shareBtn.setOnClickListener {
            var i = Intent(this, ShareOneActivity::class.java)
            startActivity(i)
        }
        tweenBtn.setOnClickListener {
            var i = Intent(this, TweenActivity::class.java)
            startActivity(i)
        }
        intentBtn.setOnClickListener {
            var i = Intent(this, First_Activity::class.java)
            startActivity(i)
        }
    }
}
