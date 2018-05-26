package com.techapp.james.iguidemo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.techapp.james.iguidemo.R
import com.techapp.james.iguidemo.titleViewModel.TitleHolder

/**
 * Created by James on 2018/3/12.
 */
class MainAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private var data: ArrayList<Any>
    private var mContext: Context
    private val VIEW_TYPE_TITLE = 0
    private val VIEW_TYPE_CONTENT = 1

    constructor(data: ArrayList<Any>, context: Context) {
        this.data = data
        this.mContext = context
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return VIEW_TYPE_TITLE
        }
        return VIEW_TYPE_CONTENT
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        when (viewType) {
            VIEW_TYPE_TITLE -> {
                var view = LayoutInflater.from(mContext).inflate(R.layout.item_title, parent, false)
                viewHolder = TitleHolder(view, mContext)
            }
            VIEW_TYPE_CONTENT -> {
                var view = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false)
                viewHolder = ItemHolder(view, mContext)
                (viewHolder as ItemHolder).limitText()
            }
        }

        return viewHolder!!
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as SetData).setData(data[position])

    }
}