package com.techapp.james.slideimagedemo

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.view.ViewPager
import android.text.Html
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var data: ArrayList<String>? = null
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        data = ArrayList<String>()

        data!!.add("1")
        data!!.add("2")
        data!!.add("2")
        data!!.add("2")
        data!!.add("2")
        addBottomDots(0)
        viewPager.adapter = ViewPagerAdapter(data!!, this)
        viewPager.clipToPadding = false//if set false mean show next page
        viewPager.setPadding(100, 0, 100, 0)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onPageSelected(position: Int) {
                addBottomDots(position)
            }

        })
        scrollBtn.setOnClickListener {
            viewPager.setCurrentItem(5, true)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun addBottomDots(currentPage: Int) {
        var dots = arrayOfNulls<TextView>(data!!.size)
        docLayout.removeAllViews()
        for (i in 0..dots!!.size - 1) {
            dots!![i] = TextView(this)
            dots!![i]!!.text = Html.fromHtml("&#8226;", Html.FROM_HTML_MODE_LEGACY) //draw circle
            /**
            FROM_HTML_MODE_COMPACT：html元素之間使用一個换行符號分隔
            FROM_HTML_MODE_LEGACY：html元素之間使用兩個换行符號分隔
             * **/
            dots!![i]!!.textSize = 35f
            dots!![i]!!.setTextColor(Color.GRAY)
            docLayout.addView(dots!![i])
        }
        dots!![currentPage]!!.setTextColor(Color.GREEN)
    }

}
