package com.techapp.james.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.techapp.james.myapplication.Interpolator.InterpolatorActivity
import com.techapp.james.myapplication.activityTransitions.First_Activity
import com.techapp.james.myapplication.animationSet.AnimationSetActivity
import com.techapp.james.myapplication.frameAnimation.FrameActivity
import com.techapp.james.myapplication.objectAnimation.ObjectActivity
import com.techapp.james.myapplication.shareElements.ShareOneActivity
import com.techapp.james.myapplication.tweenAnimation.TweenActivity
import com.techapp.james.myapplication.valueAnimation.ValueActivity
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

        valueBtn.setOnClickListener {
            var i = Intent(this, ValueActivity::class.java)
            startActivity(i)
        }
        objectBtn.setOnClickListener {
            var i = Intent(this, ObjectActivity::class.java)
            startActivity(i)
        }
        animationSetBtn.setOnClickListener {
            var i = Intent(this, AnimationSetActivity::class.java)
            startActivity(i)
        }
        interpolatorBtn.setOnClickListener {
            var i = Intent(this, InterpolatorActivity::class.java)
            startActivity(i)
        }
    }
}
