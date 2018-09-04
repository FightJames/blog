package com.techapp.james.recycleviewcustomer.customRecycler

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.techapp.james.recycleviewcustomer.R
import kotlinx.android.synthetic.main.recylerview_item_image.view.*

/**
 * Created by James on 2018/3/1.
 */
class MyImageHolder : RecyclerView.ViewHolder {
    public var imageView: ImageView? = null

    constructor(itemView: View) : super(itemView) {
        //here can get item layout's components
        imageView = itemView.dragImageView
        imageView!!.setImageResource(R.drawable.abc_ic_clear_material)
        imageView!!.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                println("Image click ")
            }
        })
    }
}