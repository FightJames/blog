package com.techapp.james.stickyrecyclerview

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.android.synthetic.main.title_item.view.*
import java.util.*

class MyAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    val TITLE = 1;
    val ITEM = 0;
    val test: Test

    constructor(test: Test) {
        this.test = test
//        test.insertOrUpdate("台灣", "台南")
//        test.insertOrUpdate("台灣", "斗六")
//        test.insertOrUpdate("台灣", "台北")
//        test.insertOrUpdate("台灣", "台東")
//
//        test.insertOrUpdate("中國", "廣東")
//        test.insertOrUpdate("中國", "福建")
//        test.insertOrUpdate("中國", "北京")
//
//        test.insertOrUpdate("美國", "")
//        test.insertOrUpdate("中國", "福建")
//        test.insertOrUpdate("中國", "北京")

    }

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
        return test.count();
    }

    var flag = true

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TitleHolder) {
            holder.textView.text = test.getItem(position)
        } else {
            (holder as ItemHolder).textView.text = test.getItem(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (test.isTitle(test.getItem(position))) {
            return TITLE
        } else {
            return ITEM
        }

    }

    class TitleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView = itemView.titleTextView
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView = itemView.textView
    }
}