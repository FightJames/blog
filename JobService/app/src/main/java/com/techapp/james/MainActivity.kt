package com.techapp.james

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        doJob()
        stopJobBtn.setOnClickListener {
            val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            scheduler.cancel(1)
        }
        stopJobBtn2.setOnClickListener {
            val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            scheduler.cancel(2)

        }
    }

    fun doJob() {
        var bundle = PersistableBundle()
        bundle.putString("data", "James task1")
        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val builder = JobInfo.Builder(1, ComponentName(this, MyJobService::class.java))  //assign service
        builder.setMinimumLatency(TimeUnit.MILLISECONDS.toMillis(5000)) // delay start time
        builder.setExtras(bundle)
        jobScheduler.schedule(builder.build())

        bundle = PersistableBundle()
        bundle.putString("data", "James task2")
        val builder2 = JobInfo.Builder(2, ComponentName(this, MyJobService::class.java))
        builder2.setMinimumLatency(TimeUnit.MILLISECONDS.toMillis(10))
        builder2.setExtras(bundle)
        jobScheduler.schedule(builder2.build())
    }
}
