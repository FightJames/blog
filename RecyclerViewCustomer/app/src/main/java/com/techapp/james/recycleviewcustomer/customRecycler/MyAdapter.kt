package com.techapp.james.recycleviewcustomer.customRecycler

import android.app.Activity

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import com.techapp.james.recycleviewcustomer.R

/**
 * Created by James on 2018/3/1.
 */
class MyAdapter : RecyclerView.Adapter<ViewHolder> {
    private val TYPE_TEXT = 0
    private val TYPE_IMAGE = 1

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val type = getItemViewType(position)
        when (type) {
            TYPE_TEXT -> (holder as MyViewHolder).textView!!.setText("TEXT_" + position)
            TYPE_IMAGE ->  print("Image View")// (holder as MyImageHolder).imageView!!.setImageResource(R.drawable.image)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) TYPE_IMAGE else TYPE_TEXT
    }
    //  private val count = 20
    private var activity: Activity? = null
    var dataList:List<String>

    constructor(activity: Activity,dataList:List<String>) {
        this.activity = activity
        this.dataList=dataList
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {

        var holder: ViewHolder? =null
        if(viewType%2==0) {
            var view = LayoutInflater.from(activity!!.applicationContext).inflate(R.layout.recyclerview_item, parent, false)
             holder = MyViewHolder(view)
        }else{

            var view = LayoutInflater.from(activity!!.applicationContext).inflate(R.layout.recylerview_item_image, parent, false)
             holder= MyImageHolder(view)
        }
        return holder!!
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}