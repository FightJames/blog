package com.techapp.james.recycleviewcustomer.speedRecycler

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.techapp.james.recycleviewcustomer.R

class SpeedAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private var context: Context
    private var list: ArrayList<String>

    constructor(context: Context, list: ArrayList<String>) {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.speed_recycler_item, parent, false)
        return ItemHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        holder as ItemHolder
//        holder.itemView.setOnTouchListener { v, event ->
//            true
//        }
        holder.textView.text=list[position]
    }

    class ItemHolder : RecyclerView.ViewHolder {
        var textView: TextView

        constructor(itemView: View) : super(itemView) {
            textView = itemView.findViewById(R.id.textView)
        }
    }
}