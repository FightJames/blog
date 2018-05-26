package com.techapp.james.iguidemo.titleViewModel

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.techapp.james.iguidemo.R

/**
 * Created by James on 2018/3/13.
 */
class ContentRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private var context: Context
    private var data: ArrayList<Any>

    constructor(context: Context, data: ArrayList<Any>) {
        this.data = data
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var titleItemHolder: TitleItemHolder
        var view = LayoutInflater.from(context).inflate(R.layout.title_content_item, parent, false)
        titleItemHolder = TitleItemHolder(view)
        return titleItemHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
    }
}