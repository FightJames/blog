package com.techapp.james.myapplication.animationSet

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationSet
import com.techapp.james.myapplication.R
import kotlinx.android.synthetic.main.activity_animation_set.*

class AnimationSetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation_set)


        sameBtn.setOnClickListener {

            var animatorSet = AnimatorSet()
            var obA = ObjectAnimator.ofFloat(leftImageView, "translationY", 0f, 800f)
            var obA1 = ObjectAnimator.ofFloat(rightImageView, "translationY", 0f, -800f)
            obA.duration = 1000
            obA.repeatCount = 1
            obA.repeatMode = ValueAnimator.REVERSE
            obA1.duration = 1000
            obA1.repeatCount = 1
            obA1.repeatMode = ValueAnimator.REVERSE
            animatorSet.playTogether(obA, obA1)
            animatorSet.start()
        }

        afterBtn.setOnClickListener {

            var animatorSet = AnimatorSet()
            var obA = ObjectAnimator.ofFloat(leftImageView, "translationY", 0f, 800f)
            var obA1 = ObjectAnimator.ofFloat(rightImageView, "translationY", 0f, -800f)
            obA.duration = 1000
            obA.repeatCount = 1
            obA.repeatMode = ValueAnimator.REVERSE
            obA1.duration = 1000
            obA1.repeatCount = 1
            obA1.repeatMode = ValueAnimator.REVERSE
            animatorSet.play(obA1).after(obA)
            animatorSet.start()
        }
    }
}
