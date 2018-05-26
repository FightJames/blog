package com.techapp.james.expandablelistviewdemo

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.group_item.view.*

/**
 * Created by James on 2018/3/4.
 */
class GroupHolder {
    var groupImageView: ImageView
    var groupTextView: TextView

    constructor(groupView: View) {
        groupImageView = groupView.groupImageView
        groupTextView = groupView.groupTextView
    }
}