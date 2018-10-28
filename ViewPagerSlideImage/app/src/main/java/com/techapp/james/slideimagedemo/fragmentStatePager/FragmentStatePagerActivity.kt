package com.techapp.james.slideimagedemo.fragmentStatePager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.techapp.james.slideimagedemo.R
import kotlinx.android.synthetic.main.fragment_state_pager_activity.*

class FragmentStatePagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_state_pager_activity)
        var fmList = ArrayList<Fragment>()
        fmList.add(ItemFragment.newInstance(0))
        fmList.add(ItemFragment.newInstance(1))
        fmList.add(ItemFragment.newInstance(2))
        fmList.add(ItemFragment.newInstance(3))
        fmList.add(ItemFragment.newInstance(4))
        fmList.add(ItemFragment.newInstance(5))
        fmList.add(ItemFragment.newInstance(6))
        var fragmentAdapter = ContentStateAdapter(supportFragmentManager, fmList)
        viewPager.adapter = fragmentAdapter
    }
}
