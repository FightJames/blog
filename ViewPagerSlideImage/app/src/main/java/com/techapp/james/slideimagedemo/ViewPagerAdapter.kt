package com.techapp.james.slideimagedemo

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_layout.view.*
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
        //view.imageView.setImageBitmap(data[position])
        container!!.addView(view)
        return view
    }

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view == `object`  // create view is same data object?
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        var view: View = `object` as View
        container!!.removeView(view)
    }
}