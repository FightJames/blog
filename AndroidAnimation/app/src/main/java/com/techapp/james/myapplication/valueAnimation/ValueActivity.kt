package com.techapp.james.myapplication.valueAnimation

import android.animation.ValueAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.techapp.james.myapplication.R
import kotlinx.android.synthetic.main.activity_value.*

class ValueActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_value)
        val animator = ValueAnimator.ofFloat(0f, 300f)
        animator.duration = 3000
        animator.repeatCount = -1
        animator.repeatMode = ValueAnimator.REVERSE
        var flag = true
        animator.addUpdateListener { valueAnimatior ->
            textView.translationY = valueAnimatior.animatedValue as Float
        }
        startBtn.setOnClickListener {
            if (flag) {
                animator.start()
                flag = false
            } else {
                animator.end()
                flag = true
            }
        }
    }
}
