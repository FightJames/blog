package com.techapp.james.expandablelistviewdemo

import android.view.View
import android.widget.CheckBox
import kotlinx.android.synthetic.main.item_item.view.*

/**
 * Created by James on 2018/3/4.
 */
class ItemHolder {
    var itemCheckBox:CheckBox
    constructor(itemView: View){
        itemCheckBox=itemView.itemCheckBox
    }
}