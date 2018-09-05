package com.techapp.james.recycleviewcustomer.dragAndSwip

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techapp.james.recycleviewcustomer.R
import kotlinx.android.synthetic.main.drag_swip_item.view.*
import java.util.*

class MyAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>, ItemTouchHelperAdapter {
    val data: ArrayList<String>
    var dragFunction: ((viewHolder: RecyclerView.ViewHolder) -> Unit)? = null

    constructor(data: ArrayList<String>) {
        this.data = data
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition..toPosition - 1) {
                Collections.swap(data, i, i + 1)
            }
        } else {
            Log.d("MyAdapter", " $fromPosition $toPosition")
            for (i in fromPosition downTo (toPosition + 1)) {
                Collections.swap(data, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v = LayoutInflater.from(parent.context).inflate(R.layout.drag_swip_item, parent, false)
        return ItemViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ItemViewHolder
        holder.textView.text = data.elementAt(position)
        holder.dragImageView.setOnTouchListener { v, event ->
            dragFunction?.let {
                it.invoke(holder)
            }
            false
        }
    }


    class ItemViewHolder(item: View) : RecyclerView.ViewHolder(item), ItemTouchHelperViewHolder {
        val textView = item.textView
        val dragImageView = item.dragImageView

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.GRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }

    }

}