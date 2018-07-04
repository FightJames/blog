package com.techapp.james.tab

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class CollectionPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
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

    override fun getCount(): Int = 100

}