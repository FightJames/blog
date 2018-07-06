package com.techapp.james.service.jobSheduler

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

class JobSchedulerService : JobService() {
    companion object {
        private var TAG = "MY_JOB_SCHEDULER_SERVICE"
        private var jobCancelled = false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d(TAG, " Job cancelled before completion")
        jobCancelled = true
        return true
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d(TAG, " Job started")
        doBackgroundWork(params)
        //return true : It means task will take a lot of time, it needs to call onStopJob to destroy
        //return false : It means task will finish in here(return).
        return true
    }

    private fun doBackgroundWork(params: JobParameters?) {
        Thread(object : Runnable {
            override fun run() {
                for (i in 0..10) {
                    Log.d(TAG, " run: " + i)
                    if (jobCancelled) {
                        return
                    }
                    try {
                        Thread.sleep(1000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
                Log.d(TAG, " Job finished")
                jobFinished(params, false)
            }
        }).start()
    }
}