package com.techapp.james.musicdemo.view.fragmentPage.albumFragment

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.first_fragment_songlist_recyclerview_item_layout.view.*

/**
 * Created by James on 2018/3/24.
 */
class ItemViewHolder : RecyclerView.ViewHolder {
    var songTextView: TextView
    var ectImageView: ImageView

    constructor(itemView: View?) : super(itemView) {
        songTextView = itemView!!.songTextView
        ectImageView = itemView!!.ectImageView
    }
}