package com.techapp.james.myapplication.shareElements


import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

import android.util.Pair
import com.techapp.james.myapplication.R
import kotlinx.android.synthetic.main.activity_main.*

class ShareOneActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intentButton.setOnClickListener {
            var i = Intent(this, ShareTwoActivity::class.java)
            var p: Pair<View, String> = Pair(imageView as View, "Image")
            var p2: Pair<View, String> = Pair(textViewHello as View, "Hello")
            var options = ActivityOptions.makeSceneTransitionAnimation(this, p, p2)
            startActivity(i, options.toBundle())
        }

    }
}
