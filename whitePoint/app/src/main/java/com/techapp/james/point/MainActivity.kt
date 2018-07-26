package com.techapp.james.point

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val allPermission = ArrayList<Int>()
    private val permission = arrayOf(Manifest.permission.SYSTEM_ALERT_WINDOW)  //權限項目在此新增
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init() {
//        applyRight()
        if (!Settings.canDrawOverlays(applicationContext)) {
            var i = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + packageName))
            startActivityForResult(i, 1)
        }
        showBtn.setOnClickListener {
            var i = Intent(this, WindowService::class.java)
            startService(i)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun applyRight(): Boolean {
        for (i in permission.indices) {
            val eachPermission = this.checkSelfPermission(permission[i]) //命名權限
            allPermission.add(eachPermission)
        }
        for (i in allPermission.indices) {
            if (allPermission[i] != PackageManager.PERMISSION_GRANTED) {
                // System.out.println(permission[serviceIntent]);
                Log.d("mainactivity ", "not per")
                this.requestPermissions(permission,
                        0)
                return false
            } else {
                Log.d("mainactivity ", "yes per")
            }

        }
        return true
    }
}
