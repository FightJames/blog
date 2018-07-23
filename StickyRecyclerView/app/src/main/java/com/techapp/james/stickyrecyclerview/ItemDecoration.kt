package com.techapp.james.stickyrecyclerview

import android.graphics.Canvas
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.title_item.view.*


class ItemDecoration : RecyclerView.ItemDecoration {
    val TAG = "ItemDecoration"
    private lateinit var currentTitleView: View
    private var preBottom: Int = 0
    private var currentView: View? = null
    private var isDropDown = false
    private var lastTitle: String = ""
    private val titleList: ArrayList<String>

    constructor(titleList: ArrayList<String>) {
        this.titleList = titleList
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state);
        var index = (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        var holder = parent.findViewHolderForAdapterPosition(index)

        if (holder is MyAdapter.TitleHolder) {
            if (currentView == null) {
                currentView = LayoutInflater.from(parent.context).inflate(R.layout.title_item, parent, false)
            }
            currentTitleView = currentView!!
            currentTitleView.titleTextView.text = holder.textView.text
            if (!titleList.contains(holder.textView.text.toString())) {
                titleList.add(holder.textView.text.toString())
            }
            val measureWidth = View.MeasureSpec.makeMeasureSpec(holder.itemView.width, View.MeasureSpec.EXACTLY)
            val measuredHeight = View.MeasureSpec.makeMeasureSpec(holder.itemView.height, View.MeasureSpec.EXACTLY)
            currentTitleView.measure(measureWidth, measuredHeight)
//            Log.d("WH", holder.itemView.width.toString() + "  " + holder.itemView.height.toString())
            currentTitleView.layout(0, 0, holder.itemView.width, holder.itemView.height)
            currentTitleView.draw(c!!)
        } else {
            val measureWidth = View.MeasureSpec.makeMeasureSpec(holder.itemView.width, View.MeasureSpec.EXACTLY)
            val measuredHeight = View.MeasureSpec.makeMeasureSpec(holder.itemView.height, View.MeasureSpec.EXACTLY)
            currentTitleView.measure(measureWidth, measuredHeight)
//            Log.d("WH", holder.itemView.width.toString() + "  " + holder.itemView.height.toString())
            var nextHolder = parent.findViewHolderForAdapterPosition(index + 1)
            if (nextHolder is MyAdapter.TitleHolder) {
                val bottom = Math.min(holder.itemView.height, nextHolder.itemView.top)
                currentTitleView.layout(0, 0, holder.itemView.width, bottom)
                if (preBottom != bottom) {
                    if (currentTitleView.titleTextView.text.equals(nextHolder.textView.text)) {
                        val title = titleList.get(titleList.indexOf(currentTitleView.titleTextView.text) - 1)
                        currentTitleView.titleTextView.text = title
                    }
                    var textView = currentTitleView.titleTextView
                    textView.bottom -= (holder.itemView.height - nextHolder.itemView.top) //just calculate distense
                    textView.top -= (holder.itemView.height - nextHolder.itemView.top)
                }
                currentTitleView.draw(c!!)
                preBottom = bottom
            } else {
                currentTitleView.layout(0, 0, holder.itemView.width, holder.itemView.height)
                currentTitleView.draw(c!!)
            }
        }
//        preIndex = index
    }
}
