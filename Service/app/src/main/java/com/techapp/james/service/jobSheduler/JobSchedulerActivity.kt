package com.techapp.james.service.jobSheduler

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.pm.ComponentInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import com.techapp.james.service.R
import kotlinx.android.synthetic.main.activity_job_scheduler.*

class JobSchedulerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_scheduler)
        scheduleBtn.setOnClickListener {
            var componentName = ComponentName(this, JobSchedulerService::class.java)
            var info = JobInfo.Builder(123, componentName)
                    .setRequiresCharging(false)
                    // 是否是充電狀態
                    //Specify that to run this job, the device must be charging (or be a non-battery-powered device connected to permanent power, such as Android TV devices).
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                    // 網路條件，default 是 NETWORK_TYPE_NONE
                    .setPersisted(true)
                    //手機重啟後是否繼續執行
                    .setPeriodic(15 * 60 * 1000)
                    //設定循環執行的時長
                    .build()
            var jobScheduler = this.getSystemService(android.content.Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            var resultCode = jobScheduler.schedule(info)
            if (resultCode == JobScheduler.RESULT_SUCCESS) {
                Log.d("Job Activity ", "Job scheduled")
            } else {
                Log.d("Job Activity ", "Job failed")
            }
        }
        cancelBtn.setOnClickListener {
            var jobScheduler = this.getSystemService(android.content.Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.cancel(123)
            Log.d("Job Activity ", "Job cancelled")
        }
    }
}
