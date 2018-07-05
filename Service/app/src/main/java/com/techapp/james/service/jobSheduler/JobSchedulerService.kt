package com.techapp.james.service.jobSheduler

import android.app.job.JobParameters
import android.app.job.JobService

class JobSchedulerService : JobService() {
    override fun onStopJob(params: JobParameters?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    override fun onStartJob(params: JobParameters?): Boolean {

        //return true : It means task will take a lot of time, it needs to call onStopJob to destroy
        //return false : It means task will finish in here(return).
        return false
    }
}