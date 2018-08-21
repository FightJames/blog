package com.techapp.james.roomwordsample.repo

import android.arch.lifecycle.LiveData
import android.content.Context
import android.os.AsyncTask
import com.techapp.james.roomwordsample.data.Word
import com.techapp.james.roomwordsample.data.WordDao
import com.techapp.james.roomwordsample.data.WordRoomDatabase

class Repository {
    //    a suggested best practice for code separation and architecture. A Repository class handles data operations. It provides a clean API to the rest of the app for app data.
//    manages query threads and allows you to use multiple backends
    private var wordDao: WordDao? = null
    private var mAllWords: LiveData<List<Word>>? = null

    constructor(context: Context) {
        var db = WordRoomDatabase.getInstance(context)
        wordDao = db.getWordDao()
        mAllWords = wordDao!!.getAllWords()
    }

    fun insert(word: Word) {
        insertAsyncTask(wordDao!!).execute(word)
    }

    fun getAllWords(): LiveData<List<Word>> {
        return mAllWords!!
    }

    companion object {
        private class insertAsyncTask : AsyncTask<Word, Unit, Unit> {
            private var asyncTaskWordDao: WordDao

            constructor(dao: WordDao) {
                asyncTaskWordDao = dao
            }

            override fun doInBackground(vararg params: Word?) {
                asyncTaskWordDao.insert(params[0]!!)
            }
        }
    }
}