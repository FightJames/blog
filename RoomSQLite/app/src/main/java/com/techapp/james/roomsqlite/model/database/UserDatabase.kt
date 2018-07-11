package com.techapp.james.roomsqlite.model.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.techapp.james.roomsqlite.model.User
import com.techapp.james.roomsqlite.model.UserDao


@Database(entities = arrayOf(User::class), version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        const val DBNAME = "User"
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase? {
            if (INSTANCE == null) {
                synchronized(UserDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder<UserDatabase>(context,
                                UserDatabase::class.java,
                                DBNAME)
                                .allowMainThreadQueries()
                                .build()
                    }
                }
            }
            return INSTANCE
        }

        fun destoryInstance() {
            if (INSTANCE != null) {
                INSTANCE!!.close()
            }
            INSTANCE = null
        }
    }
}