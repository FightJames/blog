package com.techapp.james.slideimagedemo.fragmentPager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import android.view.ViewGroup

class ContentPagerAdapter(fm: FragmentManager, val fmList: ArrayList<Fragment>) : FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        return fmList[position]
    }

    override fun getCount(): Int = fmList.size

}