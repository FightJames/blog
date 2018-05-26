package com.techapp.james.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private val allPermission = ArrayList<Int>()
    private val permission = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)  //權限項目在此新增
    val REQUEST_CODE_ASK_PERMISSIONS = 0
    var mp3List = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        applyRight()
        getMP3()
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.adapter=MyAdapter(mp3List,this)
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun applyRight() {
        for (i in permission.indices) {
            val eachPermission = checkSelfPermission(permission[i]) //命名權限
            allPermission.add(eachPermission)
        }
        for (i in allPermission.indices) {
            if (allPermission[i] != PackageManager.PERMISSION_GRANTED) {
                // System.out.println(permission[i]);
                requestPermissions(permission,
                        REQUEST_CODE_ASK_PERMISSIONS)
                return
            }
        }
    }

    fun getMP3() {
        var cursor =
                this.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        null, null, null, null)
        println("Cursor count " + cursor.count)
        if (cursor != null) {
            cursor.moveToFirst()
            while (cursor.moveToNext()) {
                var url = cursor.getString(
                        cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))

                println("Music path " + url)
                mp3List.add(url)
            }
        }
    }
}
