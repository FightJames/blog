package com.techapp.james.tab

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mCollectionPagerAdapter: CollectionPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mCollectionPagerAdapter = CollectionPagerAdapter(supportFragmentManager)
        pager.adapter = mCollectionPagerAdapter
    }
}