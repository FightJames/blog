package com.techapp.james.iguidemo.titleViewModel

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.techapp.james.iguidemo.adapter.SetData
import kotlinx.android.synthetic.main.item_title.view.*

/**
 * Created by James on 2018/3/12.
 */
class TitleHolder : RecyclerView.ViewHolder, SetData {
    private var context: Context
    override fun setData(data: Any) {
    }

    constructor(itemView: View?, context: Context) : super(itemView) {
        this.context = context
        itemView!!.contentRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        //LinearLayoutManager(Context context, int orientation, boolean reverseLayout)
        var data = ArrayList<Any>()
        for(i in 0..10){
            data.add("1")
        }
        itemView!!.contentRecycler.adapter=ContentRecyclerViewAdapter(context,data)
    }
}