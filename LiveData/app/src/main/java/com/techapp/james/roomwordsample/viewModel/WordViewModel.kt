package com.techapp.james.roomwordsample.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.techapp.james.roomwordsample.repo.Repository
import com.techapp.james.roomwordsample.data.Word

class WordViewModel : AndroidViewModel {
    private var wordRepo: Repository
    private var mAllWords: LiveData<List<Word>>

    constructor(application: Application) : super(application) {
        wordRepo = Repository(application)
        mAllWords = wordRepo.getAllWords()
    }

    fun getAllWords(): LiveData<List<Word>> {
        return mAllWords
    }

    fun insert(word: Word) {
        wordRepo.insert(word)
    }

}
