package com.techapp.james.myapplication.animationListener

import android.view.animation.Animation
import android.widget.Button

/**
 * Created by James on 2018/3/8.
 */
class BigListener : Animation.AnimationListener {
    var target: Button

    constructor(target: Button) {
        this.target = target
    }

    override fun onAnimationRepeat(p0: Animation?) {
    }

    override fun onAnimationEnd(p0: Animation?) {
//        target!!.scaleX = 2f
//        target!!.scaleY = 2f
    }

    override fun onAnimationStart(p0: Animation?) {
    }
}