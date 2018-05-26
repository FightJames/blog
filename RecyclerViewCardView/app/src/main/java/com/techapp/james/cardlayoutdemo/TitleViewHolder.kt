package com.techapp.james.cardlayoutdemo

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.title_layout.view.*

/**
 * Created by James on 2018/3/6.
 */
class TitleViewHolder : RecyclerView.ViewHolder {
    var clayout: ConstraintLayout

    constructor(itemView: View?,width:Int) : super(itemView) {
        clayout = itemView!!.clayout

    }
}