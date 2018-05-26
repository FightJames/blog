package com.techapp.james.cardlayoutdemo

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by James on 2018/3/6.
 */
class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private var size: Int = 0
    private var activity: Activity?

    constructor(s: Int, activity: Activity) {
        size = s
        this.activity = activity
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder
        when (viewType) {
            0 -> {
                activity!!.window.decorView.width
                var view = LayoutInflater.from(activity!!.applicationContext).inflate(R.layout.title_layout, parent,false)
                viewHolder = TitleViewHolder(view,activity!!.window.decorView.width)
            }
            1 -> {
                var view = LayoutInflater.from(activity!!.applicationContext).inflate(R.layout.item_layout, parent,false)
                viewHolder = ItemViewHolder(view)

            }
            else -> {
                var view = LayoutInflater.from(activity!!.applicationContext).inflate(R.layout.item_picture_layout, null)
                viewHolder = ItemPictureViewHolder(view)
            }
        }
        return viewHolder
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return size
    }
}