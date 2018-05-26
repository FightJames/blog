package com.techapp.james.chatroomdemo.model.thread

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import com.squareup.picasso.Picasso
import com.techapp.james.chatroomdemo.model.compressImage.CompressImage

/**
 * Created by James on 2018/4/24.
 */
class ImageLoad : Runnable {
    private var activity: Activity
    private var imageUri: Uri
    private var callBack: CallBack = object : CallBack {
        override fun callBack(bitmap: Bitmap) {
        }
    }

    constructor(activity: Activity, uri: Uri, callBack: CallBack) {
        this.activity = activity
        this.callBack = callBack
        this.imageUri = uri
    }

    override fun run() {
        var bitmap = CompressImage.compressQuality(imageUri, activity)
        activity.runOnUiThread(object : Runnable {
            override fun run() {
                callBack.callBack(bitmap)
            }
        })
    }

    interface CallBack {
        fun callBack(bitmap: Bitmap)
    }
}