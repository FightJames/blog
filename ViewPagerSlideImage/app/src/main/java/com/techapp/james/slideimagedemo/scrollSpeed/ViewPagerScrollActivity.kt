package com.techapp.james.slideimagedemo.scrollSpeed

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.techapp.james.slideimagedemo.R
import kotlinx.android.synthetic.main.activity_view_pager_scroll.*
import java.lang.reflect.Field

class ViewPagerScrollActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_scroll)
        setScrollSpeed()
        var list = ArrayList<String>()
        for (i in 0..100) {
            list.add("1")
        }
        viewPager.adapter = ViewPagerAdapter(list, this)
        scrollBtn.setOnClickListener {
            viewPager.setCurrentItem(100, true)
        }
    }

    private fun setScrollSpeed() {
        try {
            var mScroller: Field

            mScroller = viewPager::class.java.getDeclaredField("mScroller")
            mScroller.isAccessible = true
            var cScroller = CustomScroller(viewPager.context)
            mScroller.set(viewPager, cScroller)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
