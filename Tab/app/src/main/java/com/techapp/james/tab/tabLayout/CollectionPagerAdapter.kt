package com.techapp.james.tab.tabLayout

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import com.techapp.james.tab.R
import kotlinx.android.synthetic.main.tab_layout_item.view.*

class CollectionPagerAdapter(fm: FragmentManager, var context: Context) : FragmentStatePagerAdapter(fm) {
    companion object {
        const val ARG_OBJECT = "object"
    }

    override fun getItem(position: Int): Fragment {
        var fragmentDemo = DemoFragmentTab()
        fragmentDemo.arguments = Bundle().apply {
            putInt(ARG_OBJECT, position + 1)
        }
        return fragmentDemo
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "OBJECT " + (position + 1)
    }

    override fun getCount(): Int = 4
}