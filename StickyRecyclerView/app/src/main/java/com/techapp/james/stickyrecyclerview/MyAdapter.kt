package com.techapp.james.stickyrecyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class MyAdapter(var data: ArrayList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val TITLE = 1;
    val ITEM = 0;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TITLE -> {
                var itemView = LayoutInflater.from(parent.context).inflate(R.layout.title_item, parent, false)
                return TitleHolder(itemView)
            }
            else -> {
                var itemView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)

                return ItemHolder(itemView)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    var flag = true
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        if (holder is TitleHolder) {
//            if (flag) {
//                holder.itemView.setBackgroundResource(R.color.colorAccent)
//                flag = false
//            }else{
//                flag=true
//            }
//        }
    }

    override fun getItemViewType(position: Int): Int {
        if (data[position].equals("台灣")) {
            return TITLE
        } else {
            return ITEM
        }

    }

    class TitleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}