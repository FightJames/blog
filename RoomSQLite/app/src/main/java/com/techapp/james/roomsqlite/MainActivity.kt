package com.techapp.james.roomsqlite

import android.arch.persistence.room.Room
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.techapp.james.roomsqlite.model.User
import com.techapp.james.roomsqlite.model.database.UserDatabase
import com.techapp.james.roomsqlite.model.database.UserDatabase.Companion.DBNAME

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val userDao = UserDatabase.getDatabase(applicationContext)!!.userDao()

        var userDB = Room.databaseBuilder<UserDatabase>(applicationContext, UserDatabase::class.java,
                DBNAME)
                .allowMainThreadQueries()
                .build()
        var userDao = userDB.userDao()
        val u = User()
        u.name = "James"
        u.birthDay = "7/11"
        //this action can't do in mainThread
        //but we can force it do in mainThread use .allowMainThreadQueries in UserDatabase class.
        userDao.insert(u)
        //Another way is to create a thread to do query.
        val userList = userDao.allUser()
        for (i in userList.indices) {
            Log.d("MainActivity ", userList.get(i).uid.toString())
        }
    }
}
