package com.techapp.james.paginglist.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.techapp.james.paginglist.Injection
import com.techapp.james.paginglist.R
import com.techapp.james.paginglist.model.Repo
import kotlinx.android.synthetic.main.activity_main.*

class SearchRepoActivity : AppCompatActivity() {

    private lateinit var viewModel: SearchRepositoriesViewModel
    private val adapter = RepoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        applyRight()

        viewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(this)).get(SearchRepositoriesViewModel::class.java)
        recyclerView.layoutManager=LinearLayoutManager(this)
        val decor = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(decor)
//        setupScrollListener()


        initAdapter()
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        viewModel.searchRepo(query)
        initSearch(query)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, viewModel.lastQueryValue())
    }

    private fun initAdapter() {
        recyclerView.adapter = adapter
        viewModel.repos.observe(this, Observer<PagedList<Repo>> {
            Log.d("Activity", "list: ${it?.size}")
            showEmptyList(it?.size == 0)
            adapter.submitList(it)
        })
        viewModel.networkErrors.observe(this, Observer<String> {
            Toast.makeText(this, "\uD83D\uDE28 Wooops ${it}", Toast.LENGTH_LONG).show()
        })
    }

    private fun initSearch(query: String) {
        searchEditText.setText(query)

        searchEditText.setOnEditorActionListener({ _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        })
        searchEditText.setOnKeyListener({ _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        })
    }

    private fun updateRepoListFromInput() {
        //trim filt space in text
        searchEditText.text.trim().let {
            if (it.isNotEmpty()) {
                recyclerView.scrollToPosition(0)
                viewModel.searchRepo(it.toString())
                adapter.submitList(null)
            }
        }
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            emptyText.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyText.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun setupScrollListener() {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
//        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val totalItemCount = layoutManager.itemCount
//                val visibleItemCount = layoutManager.childCount
//                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
//
//                viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
//            }
//        })
    }

    companion object {
        val requestRight = 0
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = "Android"
    }


    fun applyRight() {
        var permission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET)
        //internet default is granted if declear in manifest
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.INTERNET), requestRight)
        }
    }

//    fun testGitHubApi() {
//        var service = GithubService.create()
//        searchRepo(service, "Android", 1, 2, { repos ->
//            repos.forEach { e ->
//                Log.d("Main ", "data ${e.fullName}")
//            }
//        }, { error ->
//            Log.d("Main ", "error $error")
//        })
//    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == requestRight) {
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}
