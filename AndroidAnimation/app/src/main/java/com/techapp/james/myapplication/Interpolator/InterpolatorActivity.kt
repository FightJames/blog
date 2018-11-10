package com.techapp.james.myapplication.Interpolator

import android.animation.ObjectAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.*
import com.techapp.james.myapplication.R
import kotlinx.android.synthetic.main.activity_interpolator.*

class InterpolatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interpolator)
        var animator = ObjectAnimator.ofFloat(imageView, "translationX", 0f, 360f, 0f)
        animator.duration = 2000
        anticipateBtn.setOnClickListener {
            animator.interpolator = AnticipateInterpolator()
            animator.start()
        }
        linearBtn.setOnClickListener {
            animator.interpolator = LinearInterpolator()
            animator.start()
        }
        accelerateDecelerateInterpolatorBtn.setOnClickListener {
            animator.interpolator = AccelerateDecelerateInterpolator()
            animator.start()
        }
        cycleBtn.setOnClickListener {
            animator.interpolator = CycleInterpolator(10f)
            animator.start()
        }
        anticipateOvershootBtn.setOnClickListener {
            animator.interpolator = AnticipateOvershootInterpolator()
            animator.start()
        }
        accelerateBtn.setOnClickListener {
            animator.interpolator = AccelerateInterpolator()
            animator.start()
        }
        decelerateBtn.setOnClickListener {
            animator.interpolator = DecelerateInterpolator()
            animator.start()
        }
        bounceBtn.setOnClickListener {
            animator.interpolator = BounceInterpolator()
            animator.start()
        }
        overshootBtn.setOnClickListener {
            animator.interpolator = OvershootInterpolator()
            animator.start()
        }
//        pathBtn.setOnClickListener {
//            animator.interpolator = PathInterpolator()
//            animator.start()
//        }
    }
}
