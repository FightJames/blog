package com.techapp.james.slideimagedemo.fragmentStatePager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class ContentStateAdapter(fm: FragmentManager, val fmList: ArrayList<Fragment>)
    : FragmentStatePagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        return fmList[position]
    }

    override fun getCount(): Int = fmList.size
}