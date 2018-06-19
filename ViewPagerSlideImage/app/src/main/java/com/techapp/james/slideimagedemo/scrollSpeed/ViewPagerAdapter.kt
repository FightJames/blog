package com.techapp.james.slideimagedemo.scrollSpeed

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techapp.james.slideimagedemo.R
import java.util.*

/**
 * Created by James on 2018/3/12.
 */
class ViewPagerAdapter : PagerAdapter {
    var data: ArrayList<String>
    var context: Context

    constructor(data: ArrayList<String>, context: Context) {
        this.data = data
        this.context = context
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        var view = LayoutInflater.from(context).inflate(R.layout.item_layout, container, false)
        if (position % 2 == 0) {
            view.setBackgroundColor(context.getColor(R.color.colorPrimary));
        } else {
            view.setBackgroundColor(context.getColor(R.color.colorAccent));
        }
        Log.d("instantiate"," pass")
        //view.imageView.setImageBitmap(data[position])
        container!!.addView(view)
        return view
    }

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        Log.d("isViewFromObject"," pass")
        return view == `object`  // create view is same data object?
    }

    override fun getCount(): Int {
        Log.d("getCount"," pass")
        return data.size
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        Log.d("destroyItem"," pass")
        var view: View = `object` as View
        container!!.removeView(view)
    }
}