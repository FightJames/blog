package com.techapp.james.tab.pagerTitleStrip

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.techapp.james.tab.CollectionTitlePagerAdapter
import com.techapp.james.tab.R
import kotlinx.android.synthetic.main.activity_pager_title.*

class PagerTitleActivity : AppCompatActivity() {
    private lateinit var cta:CollectionTitlePagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager_title)
        cta= CollectionTitlePagerAdapter(supportFragmentManager)
        viewPager.adapter=cta
    }
}
