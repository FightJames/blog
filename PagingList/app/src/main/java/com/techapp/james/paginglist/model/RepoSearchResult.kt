package com.techapp.james.paginglist.model

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList

data class RepoSearchResult(
        val data: LiveData<PagedList<Repo>>,
        val networkErrors: LiveData<String>
)