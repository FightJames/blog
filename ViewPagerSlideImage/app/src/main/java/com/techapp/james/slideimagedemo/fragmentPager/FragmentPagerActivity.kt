package com.techapp.james.slideimagedemo.fragmentPager

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.util.AttributeSet
import android.view.View
import com.techapp.james.slideimagedemo.R
import kotlinx.android.synthetic.main.fragment_pager_activity.*

// use fragment in view pager, but it can't use in big data.
class FragmentPagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_pager_activity)
        var fmList = ArrayList<Fragment>()
        fmList.add(ItemFragment.newInstance(0))
        fmList.add(ItemFragment.newInstance(1))
        fmList.add(ItemFragment.newInstance(2))
        fmList.add(ItemFragment.newInstance(3))
        fmList.add(ItemFragment.newInstance(4))
        fmList.add(ItemFragment.newInstance(5))
        fmList.add(ItemFragment.newInstance(6))
        var fragmentAdapter = ContentPagerAdapter(supportFragmentManager, fmList)
        viewPager.adapter = fragmentAdapter
    }

}
