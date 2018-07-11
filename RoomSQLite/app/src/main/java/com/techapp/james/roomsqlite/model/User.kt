package com.techapp.james.roomsqlite.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.techapp.james.roomsqlite.model.User.Companion.TableName

@Entity(tableName = TableName)
class User {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
    @ColumnInfo
    var name: String? = null

    @ColumnInfo
    var birthDay: String? = null

    companion object {
        const val TableName = "TABLE_USER"
    }
}