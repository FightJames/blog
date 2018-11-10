package com.techapp.james.myapplication.objectAnimation

import android.animation.ObjectAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.techapp.james.myapplication.R
import kotlinx.android.synthetic.main.activity_object.*

class ObjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_object)
        var animator = ObjectAnimator.ofFloat(imageView, "rotationX", 0f, 360f, 0f)
        animator.setDuration(2000);
        var flag = true
        startBtn.setOnClickListener {
            if (flag) {
                animator.start();
                flag = false
            } else {
                animator.cancel()
                flag = true
            }
        }
    }
}
