package com.techapp.james.musicdemo.view.main

import android.widget.ImageView

/**
 * Created by James on 2018/3/20.
 */
class BottomImage {
    private var imageView: ImageView
    private var notClickBackground: Int
    private var onClickBackground: Int

    constructor(imageView: ImageView, notClickBackground: Int, onClickBackground: Int) {
        this.imageView = imageView
        this.notClickBackground = notClickBackground
        this.onClickBackground = onClickBackground
    }

    fun setNotClickBackground() {
        imageView.setImageResource(notClickBackground)
    }

    fun setOnClickBackground() {
        imageView.setImageResource(onClickBackground)
    }

    fun equal(target: Any): Boolean {
        if (target.equals(imageView)) {
            return true
        }
        return false
    }
}