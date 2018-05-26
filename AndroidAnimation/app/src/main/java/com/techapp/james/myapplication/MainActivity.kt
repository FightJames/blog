package com.techapp.james.myapplication


import android.app.ActivityOptions
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import com.techapp.james.myapplication.animationListener.BigListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var buttonAnimation: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_bomb)
        var imageMoveAnimation = TranslateAnimation(10f, 200f, 10f, 500f)
        imageMoveAnimation.duration = 2000
        imageMoveAnimation.repeatCount = -1
        imageView.animation = imageMoveAnimation
        imageMoveAnimation.startNow()

        var imageStopAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_object)
        imageView2.animation = imageStopAnimation
        imageStopAnimation.startNow()
        button.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                if (MotionEvent.ACTION_DOWN == p1!!.action) {
                    buttonAnimation!!.setAnimationListener(BigListener(button))
                    button.animation = buttonAnimation
                    buttonAnimation!!.fillAfter = true
                    buttonAnimation!!.startNow()
                } else if (MotionEvent.ACTION_UP == p1!!.action) {
                    // buttonAnimation!!.cancel()
                    button.clearAnimation()
                }
                return false
            }
        })

        button.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, Main2Activity::class.java)
            var activityOption = ActivityOptions.makeCustomAnimation(this, R.anim.abc_fade_in,
                    R.anim.anim_out)
            //this.startActivity(intent,activityOption.toBundle())
            //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            this.startActivity(intent)
            overridePendingTransition(R.anim.abc_fade_in, R.anim.anim_out)
        }

    }
}
