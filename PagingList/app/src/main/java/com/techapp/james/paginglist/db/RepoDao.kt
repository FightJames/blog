package com.techapp.james.paginglist.db

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.techapp.james.paginglist.model.Repo

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repos: List<Repo>)

    @Query("SELECT * FROM repos Where (name LIKE :queryString) OR (description LIKE :queryString) ORDER BY stars DESC, name ASC")
    fun reposByName(queryString: String): DataSource.Factory<Int, Repo>

}