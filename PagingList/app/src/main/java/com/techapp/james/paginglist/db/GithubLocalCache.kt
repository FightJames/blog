package com.techapp.james.paginglist.db

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.util.Log
import com.techapp.james.paginglist.model.Repo
import java.util.concurrent.Executor

class GithubLocalCache(
        private val repoDao: RepoDao,
        private val ioExecutor: Executor
) {
    fun insert(repos: List<Repo>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            Log.d("GithubLocalCache", " inserting ${repos.size} repos")
            repoDao.insert(repos)
            insertFinished()
        }
    }

    fun reposByName(name: String): DataSource.Factory<Int, Repo> {
        val query = "%${name.replace(' ', '%')}%"
        return repoDao.reposByName(query)
    }
}