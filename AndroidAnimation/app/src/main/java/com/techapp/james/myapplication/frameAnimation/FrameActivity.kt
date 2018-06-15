package com.techapp.james.myapplication.frameAnimation

import android.graphics.drawable.AnimationDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.techapp.james.myapplication.R
import kotlinx.android.synthetic.main.activity_frame.*

class FrameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame)
//        imageView.setBackgroundResource(R.drawable.frame_anim)
        var animation = imageView.background as AnimationDrawable
        animation.start()
    }
}
