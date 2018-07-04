package com.techapp.james.tab.tabLayout

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.techapp.james.tab.R
import kotlinx.android.synthetic.main.activity_tab.*

class TabActivity : AppCompatActivity() {
    private lateinit var mCollectionPagerAdapter: CollectionPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)
        mCollectionPagerAdapter = CollectionPagerAdapter(supportFragmentManager, this)
        pager.adapter = mCollectionPagerAdapter
        tab.setupWithViewPager(pager)
        for (i in 0..(tab as TabLayout).tabCount - 1) {
            var tabItem = (tab as TabLayout).getTabAt(i)
            tabItem?.let {
                it.setCustomView(R.layout.tab_layout_item)
                if (i == 0) {
                    it.customView?.setBackgroundColor(resources.getColor(android.R.color.holo_green_light))
                }
                it.customView?.let {
                    var textView = it.findViewById<TextView>(R.id.textView)
                    textView.text = i.toString()
                }
            }
        }
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.setBackgroundColor(resources.getColor(android.R.color.white))
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView?.setBackgroundColor(resources.getColor(android.R.color.holo_green_light))
            }
        })
    }
}