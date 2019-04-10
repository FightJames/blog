package com.techapp.james

import android.app.job.JobParameters
import android.app.job.JobService
import android.os.Handler
import timber.log.Timber

class MyJobService : JobService {

    constructor() {
        Timber.d("JobService constructor")
    }

    var runnable1: Runnable? = null
    var runnable2: Runnable? = null
    var handler = Handler()
    @Volatile
    var flag1 = true
    @Volatile
    var flag2 = true
    var thread1 = Thread(object : Runnable {
        override fun run() {
            var i = 0
            while (flag1) {
                Timber.d("Thread is running $i ${Thread.currentThread().name}")
                i++
                Thread.sleep(5000)
                if (i == 3) {
                    flag1 = false
                    runnable1?.let {
                        Timber.d("Thread1 is finish call JobService finish")
                        handler.post(runnable1)
                    }
                }
            }
        }
    })

    var thread2 = Thread(object : Runnable {
        override fun run() {
            var i = 0
            while (flag2) {
                Timber.d("Thread is running $i ${Thread.currentThread().name}")
                i++
                Thread.sleep(5000)
                if (i == 3) {
                    flag2 = false
                    runnable2?.let {
                        Timber.d("Thread2 is finish call JobService finish")
                        handler.post(runnable2)
                    }
                }
            }
        }
    })

    override fun onStartJob(params: JobParameters): Boolean {
        params.let {
            Timber.d("JobParameter " + params.extras.getString("data", "no data"))
        }
        Timber.d("JobService onStartJob")
        if (params.jobId == 1) {
            runnable1 = Runnable {
                Timber.d("JobService Task1 finish ${params.jobId}")
                this.jobFinished(params, false)
            }
        } else {
            runnable2 = Runnable {
                Timber.d("JobService Task2 finish ${params.jobId}")
                this.jobFinished(params, false)
            }
        }
        params?.let {
            if (!thread1.isAlive) {
                if (it.jobId == 1) {
                    thread1.start()
                } else {
                    thread2.start()
                }
            }
        }
        return true
    }

    override fun onStopJob(params: JobParameters): Boolean {
        Timber.d("JobService onStopJob ${params.jobId}")
        params.let {
            if (it.jobId == 1) {
                flag1 = false
            } else {
                flag2 = false
            }
        }
        return false
    }


    override fun onDestroy() {
        super.onDestroy()
        Timber.d("JobService onDestroy")
    }
}
