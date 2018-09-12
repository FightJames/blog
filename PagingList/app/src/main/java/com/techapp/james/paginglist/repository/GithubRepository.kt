package com.techapp.james.paginglist.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.LivePagedListBuilder
import android.util.Log
import com.techapp.james.paginglist.api.GithubService
import com.techapp.james.paginglist.api.searchRepo
import com.techapp.james.paginglist.db.GithubLocalCache
import com.techapp.james.paginglist.model.RepoSearchResult

class GithubRepository(
        private val service: GithubService,
        private val cache: GithubLocalCache
) {
    //keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = 1
    // LiveData of network errors
    private val networkErrors = MutableLiveData<String>()
    //avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    fun search(query: String): RepoSearchResult {
        Log.d("GithubRepository", "New query: $query")
        lastRequestedPage = 1
//        requestAndSaveData(query)

        //get data from the local cache
        val dataSourceFactory = cache.reposByName(query)

        //Construct the boundary callback
        val boundaryCallback = RepoBoundaryCallback(query, service, cache)
        var networkErrors = boundaryCallback.networkErrors

        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build()
        return RepoSearchResult(data, networkErrors)
    }

//    fun requestMore(query: String) {
//        requestAndSaveData(query)
//    }

    private fun requestAndSaveData(query: String) {
        if (isRequestInProgress) return
        isRequestInProgress = true
        searchRepo(service, query, lastRequestedPage, NETWORK_PAGE_SIZE, { repos ->
            cache.insert(repos, {
                lastRequestedPage++
                isRequestInProgress = false
            })
        }, { error ->
            networkErrors.postValue(error)
            isRequestInProgress = false
        })

    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
        private const val DATABASE_PAGE_SIZE = 20
    }
}