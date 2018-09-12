package com.techapp.james.paginglist.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedList
import android.util.Log
import com.techapp.james.paginglist.api.GithubService
import com.techapp.james.paginglist.api.searchRepo
import com.techapp.james.paginglist.db.GithubLocalCache
import com.techapp.james.paginglist.model.Repo

class RepoBoundaryCallback(
        private val query: String,
        private val service: GithubService,
        private val cache: GithubLocalCache
) : PagedList.BoundaryCallback<Repo>() {

    private var lastRequestedPage = 1
    // LiveData of network errors
    val networkErrors = MutableLiveData<String>()
    //avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    override fun onZeroItemsLoaded() {
        Log.d("Callback ", "ItemLoad")
        requestAndSaveData(query)
    }

    override fun onItemAtEndLoaded(itemAtEnd: Repo) {
        Log.d("Callback ", "ItemEnd")
        requestAndSaveData(query)
    }

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
    }
}