package com.techapp.james.myapplication.tweenAnimation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.techapp.james.myapplication.R
import kotlinx.android.synthetic.main.activity_tween.*

class TweenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tween)
        var animation: Animation = AnimationUtils.loadAnimation(this, R.anim.anim_object)
        if (animation == null) {
            Log.d("animation ", "Null")
        }
        var i = findViewById<ImageView>(R.id.imageView)
//        imageView.startAnimation(animation)
        imageView.animation=animation
        animation.startNow()
    }
}
