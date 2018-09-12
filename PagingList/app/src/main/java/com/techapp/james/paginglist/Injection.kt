package com.techapp.james.paginglist

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.techapp.james.paginglist.api.GithubService
import com.techapp.james.paginglist.repository.GithubRepository
import com.techapp.james.paginglist.db.GithubLocalCache
import com.techapp.james.paginglist.db.RepoDatabase
import com.techapp.james.paginglist.ui.ViewModelFactory
import java.util.concurrent.Executors

object Injection {

    private fun provideCache(context: Context): GithubLocalCache {
        val database = RepoDatabase.getInstance(context)
        return GithubLocalCache(database.reposDao(), Executors.newSingleThreadExecutor())
    }

    private fun provideGithubRepository(context: Context): GithubRepository {
        return GithubRepository(GithubService.create(), provideCache(context))
    }

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(provideGithubRepository(context))
    }
}