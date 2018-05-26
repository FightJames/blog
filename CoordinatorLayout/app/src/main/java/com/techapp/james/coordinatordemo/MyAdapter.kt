package com.techapp.james.coordinatordemo

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_layout.view.*

/**
 * Created by James on 2018/3/9.
 */
class MyAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    var data: ArrayList<String>
    var context: Context

    constructor(context: Context) {
        this.context = context
        data = ArrayList()
        data.add("first")
        for (i in 0..50) {
            data.add("Data")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
        var itemHolder = ItemViewHolder(view)
        return itemHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as ItemViewHolder).textView.text = data[position]
    }

    override fun getItemCount(): Int {
        return data.size
    }

    internal class ItemViewHolder : RecyclerView.ViewHolder {
        var textView: TextView

        constructor(itemView: View?) : super(itemView) {
            textView = itemView!!.textView
        }
    }

}