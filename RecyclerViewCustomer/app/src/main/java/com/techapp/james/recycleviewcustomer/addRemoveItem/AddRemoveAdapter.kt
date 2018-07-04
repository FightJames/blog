package com.techapp.james.recycleviewcustomer.addRemoveItem

import android.content.Context
import android.support.v7.view.menu.MenuView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.techapp.james.recycleviewcustomer.R
import kotlinx.android.synthetic.main.list_add_remove_item.view.*

class AddRemoveAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    lateinit var layoutInflater: LayoutInflater
    var size = 100

    constructor(context: Context) {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var view = layoutInflater.inflate(R.layout.list_add_remove_item, parent, false)
        return ItemHolder(view)
    }

    override fun getItemCount(): Int = size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        holder as ItemHolder
        holder.textView.text = position.toString()
        holder.btn.setOnClickListener {
            this.notifyItemRemoved(position)
            size--
        }
    }

    class ItemHolder : RecyclerView.ViewHolder {
        var btn: Button
        var textView: TextView

        constructor(view: View) : super(view) {
            btn = view.findViewById(R.id.removeBtn)
            textView = view.findViewById(R.id.itemTextView)
        }
    }
}