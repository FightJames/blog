package com.techapp.james.intenttype.shareIntent

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.techapp.james.intenttype.R
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        intentBtn.setOnClickListener {
            shareTo("Line", "tt", "Share")
        }
    }

    fun shareTo(subject: String, body: String, chooserTitle: String) {
        var sharingIntent = Intent(Intent.ACTION_SEND);

        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(sharingIntent, chooserTitle))
    }
}
