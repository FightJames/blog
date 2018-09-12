package com.techapp.james.paginglist.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "repos")
data class Repo(@PrimaryKey @field:SerializedName("id") val id: Long,
                @field:SerializedName("name") val name: String,
                @field:SerializedName("full_name") val fullName: String,
                @field:SerializedName("description") val description: String?,
                @field:SerializedName("html_url") val url: String,
                @field:SerializedName("stargazers_count") val stars: Int,
                @field:SerializedName("forks_count") val forks: Int,
                @field:SerializedName("language") val language: String?)