package com.techapp.james.paginglist.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.techapp.james.paginglist.repository.GithubRepository

class ViewModelFactory(private val repository: GithubRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchRepositoriesViewModel::class.java)) {
            // Sometimes Java generics just doesn't let you do what you want to
            @Suppress("UNCHECKED_CAST")
            return SearchRepositoriesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}