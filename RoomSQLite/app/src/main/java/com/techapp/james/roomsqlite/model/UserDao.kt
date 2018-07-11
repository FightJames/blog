package com.techapp.james.roomsqlite.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface UserDao {
    @Query("select * from " + User.TableName)
    fun allUser(): List<User>

    @Query("select * from " + User.TableName + " Where uid= :uid")
    fun getUser(uid: Int): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)
}