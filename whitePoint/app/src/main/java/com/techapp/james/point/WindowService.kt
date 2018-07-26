package com.techapp.james.point

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.ImageView

class WindowService : Service() {
    private lateinit var windowManager: WindowManager
    private lateinit var imageView: ImageView
    override fun onBind(intent: Intent): IBinder {
        return MyBinder()
    }

    override fun onCreate() {
        super.onCreate()
        init()
    }

    fun init() {
        var layoutParams = WindowManager.LayoutParams()
        windowManager = application.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//val params = WindowManager.LayoutParams(
//        WindowManager.LayoutParams.WRAP_CONTENT,
//        WindowManager.LayoutParams.WRAP_CONTENT,
//        WindowManager.LayoutParams.TYPE_SYSTEM_ALERT, //TYPE_PHONE
//        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//        PixelFormat.TRANSLUCENT);
        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE

//        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        layoutParams.format = PixelFormat.RGBA_8888
        //設定 懸浮點 不可獲取focus
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        layoutParams.gravity = Gravity.TOP or Gravity.LEFT
        layoutParams.width = 200
        layoutParams.height = 200

//       layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
//        layoutParams.height =WindowManager.LayoutParams.MATCH_PARENT
//        layoutParams.x = -50
        layoutParams.y = +50
//        imageView = LayoutInflater.from(applicationContext).inflate(R.layout.window_point, null, false) as ImageView
        imageView = ImageView(this)
        imageView.setBackgroundColor(R.color.colorAccent)
        imageView.setImageResource(R.drawable.ic_android_black_24dp)
        var downX = 0f
        var downY = 0f
        imageView.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_MOVE -> {
//                    Log.d("Event Raw X and getX"," "+event.rawX+" "+event.x)
//                    Log.d("Event Raw Y and getY"," "+event.rawY+" "+event.y)
                    layoutParams.x = Math.round(event.getRawX() - downX);
                    layoutParams.y = Math.round(event.getRawY() - downY);
                    windowManager.updateViewLayout(imageView, layoutParams);
                }
                MotionEvent.ACTION_DOWN -> {
                    downX = event.getRawX() - layoutParams.x;
                    downY = event.getRawY() - layoutParams.y;
                }
                MotionEvent.ACTION_UP -> {

                }
            }
            true
        }
        windowManager.addView(imageView, layoutParams)
        imageView.setOnClickListener {
            Log.d("Suspension Image", " Pass")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        windowManager.removeView(imageView)
    }

    class MyBinder : Binder() {

    }
}
