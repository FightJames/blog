package com.techapp.james.roomwordsample.data

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.os.AsyncTask

@Database(entities = arrayOf(Word::class), version = 1,exportSchema = true)
abstract class WordRoomDatabase : RoomDatabase() {
    abstract fun getWordDao(): WordDao

    companion object {
        private var INSTANCE: WordRoomDatabase? = null
        fun getInstance(context: Context): WordRoomDatabase {
            if (INSTANCE == null) {
                synchronized(WordRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            WordRoomDatabase::class.java, "word_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build()
                }
            }
            return INSTANCE!!
        }

        private var sRoomDatabaseCallback = object : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                PopulateDbAsync(INSTANCE!!).execute()
            }
        }

        private class PopulateDbAsync : AsyncTask<Unit, Unit, Unit> {
            private val mDao: WordDao

            constructor(db: WordRoomDatabase) {
                this.mDao = db.getWordDao()
            }

            override fun doInBackground(vararg params: Unit?) {
                mDao.deleteAllWord()
                var word = Word("James")
                mDao.insert(word)
                word = Word("Neil")
                mDao.insert(word)
            }
        }

    }
}