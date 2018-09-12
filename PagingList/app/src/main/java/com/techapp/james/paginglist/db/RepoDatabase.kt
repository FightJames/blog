package com.techapp.james.paginglist.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.techapp.james.paginglist.model.Repo

@Database(entities = [Repo::class],
        version = 1,
        exportSchema = false)
abstract class RepoDatabase : RoomDatabase() {
    abstract fun reposDao(): RepoDao

    companion object {
        @Volatile
        private var INSTANCE: RepoDatabase? = null

        // ?: is null
        fun getInstance(context: Context): RepoDatabase = INSTANCE
                ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        RepoDatabase::class.java, "Github.db")
                        .build()
    }
}
