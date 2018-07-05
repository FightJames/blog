package com.techapp.james.service

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.techapp.james.service.bindService.BindServiceActivity
import com.techapp.james.service.intentService.IntentServiceActivity
import com.techapp.james.service.jobSheduler.JobSchedulerActivity
import com.techapp.james.service.normalService.NormalServiceActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        normalServiceBtn.setOnClickListener {
            var i = Intent(this, NormalServiceActivity::class.java)
            startActivity(i)
        }
        bindServiceBtn.setOnClickListener {
            var i = Intent(this, BindServiceActivity::class.java)
            startActivity(i)
        }
        intentServiceBtn.setOnClickListener {
            var i = Intent(this, IntentServiceActivity::class.java)
            startActivity(i)
        }
        jobSchedulerBtn.setOnClickListener {
            var i = Intent(this, JobSchedulerActivity::class.java)
            startActivity(i)
        }
    }
}
