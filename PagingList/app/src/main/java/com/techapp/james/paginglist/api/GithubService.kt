package com.techapp.james.paginglist.api

import android.util.Log
import com.techapp.james.paginglist.model.Repo
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val TAG = "GithubService"
private const val IN_QUALIFIER = "in:name,description"

fun searchRepo(
        service: GithubService,
        query: String,
        page: Int,
        itemPerPage: Int,
        onSuccess: (repos: List<Repo>) -> Unit,
        onError: (error: String) -> Unit) {
    Log.d(TAG, "query: $query, page: $page, itemPerPage: $itemPerPage")

    val apiQuery = query + IN_QUALIFIER

    service.searchRepo(apiQuery, page, itemPerPage).enqueue(
            object : Callback<RepoSearchResponse> {
                override fun onFailure(call: Call<RepoSearchResponse>, t: Throwable) {
                    Log.d(TAG, "fail to get data")
                    onError(t.message ?: "unknown error")
                }

                override fun onResponse(call: Call<RepoSearchResponse>, response: Response<RepoSearchResponse>) {
                    if (response.isSuccessful) {
                        val repos = response.body()?.items ?: emptyList()
                        onSuccess(repos)
                    } else {
                        onError(response.errorBody()?.string() ?: "Unknown error")
                    }
                }

            }
    )
}

interface GithubService {
    // @Query mean url ? q="query"&page="page" etc..
    @GET("search/repositories?sort=stars")
    fun searchRepo(@Query("q") query: String,
                   @Query("page") page: Int,
                   @Query("per_page") itemsPerPage: Int): Call<RepoSearchResponse>

    companion object {
        private const val BASE_URL = "https://api.github.com/"

        fun create(): GithubService {
//           val logger=HttpLoggin
            val client = OkHttpClient.Builder()
                    .build()
            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(GithubService::class.java)
        }
    }
}