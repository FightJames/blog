package com.techapp.james.paginglist.api

import com.google.gson.annotations.SerializedName
import com.techapp.james.paginglist.model.Repo

data class RepoSearchResponse(
        @SerializedName("total_count") val total: Int = 0,
        @SerializedName("items") val items: List<Repo> = emptyList<Repo>(),
        val nextPage: Int? = null)