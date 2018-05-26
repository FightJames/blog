package com.techapp.james.chatroomdemo.view.chatRoomView

import android.app.Activity
import android.util.DisplayMetrics

/**
 * Created by James on 2018/4/27.
 */
object ScreenUtils {
    fun getScreenWidth(activity: Activity): Int {
        var displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getRealMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    fun getScreenHeight(activity: Activity):Int {
        var displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getRealMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }
}