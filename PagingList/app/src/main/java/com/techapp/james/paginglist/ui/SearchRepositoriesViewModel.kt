package com.techapp.james.paginglist.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import android.util.Log
import com.techapp.james.paginglist.repository.GithubRepository
import com.techapp.james.paginglist.model.Repo
import com.techapp.james.paginglist.model.RepoSearchResult

class SearchRepositoriesViewModel(private val repository: GithubRepository) : ViewModel() {
    companion object {
        private const val VISIBLE_THREADHOLD = 5
    }

    private val queryLiveData = MutableLiveData<String>()
    // transform string liveData to RepoSearchResult liveData
    // Here return RepoSearchResult
//    {
//        repository.search(it)
//    }
    private val repoResult: LiveData<RepoSearchResult> = Transformations.map(queryLiveData, {
        repository.search(it)
    })
    // In switchMap this function must return LiveData
    //{ it ->  it.data }
    val repos: LiveData<PagedList<Repo>> = Transformations.switchMap(repoResult, { it ->
        it.data
    })
    val networkErrors: LiveData<String> = Transformations.switchMap(repoResult, { it -> it.networkErrors })

    fun searchRepo(queryString: String) {
        Log.d("ViewModel ", "search again")
        queryLiveData.postValue(queryString)
    }
//    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
//        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THREADHOLD >= totalItemCount) {
//            val immutableQuery = lastQueryValue()
//            if (immutableQuery != null) {
//                repository.requestMore(immutableQuery)
//            }
//        }
//    }

    /**
     * Get the last query value.
     */
    fun lastQueryValue(): String? = queryLiveData.value

}