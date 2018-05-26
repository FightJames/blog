package com.techapp.james.recycleviewcustomer

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.recyclerview_item.view.*

/**
 * Created by James on 2018/3/1.
 */
class MyViewHolder : RecyclerView.ViewHolder {
    public var textView: TextView? = null

    constructor(itemView: View) : super(itemView) {
        //here can get item layout's components
        textView = itemView.textView
        itemView.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
            println("text click "+textView!!.text)
            }
        })
    }
}