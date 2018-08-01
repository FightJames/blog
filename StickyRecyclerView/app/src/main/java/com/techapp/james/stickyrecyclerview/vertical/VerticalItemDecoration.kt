package com.techapp.james.stickyrecyclerview.vertical

import android.graphics.Canvas
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.techapp.james.stickyrecyclerview.dataStructure.Data
import com.techapp.james.stickyrecyclerview.R
import kotlinx.android.synthetic.main.title_item.view.*


class VerticalItemDecoration : RecyclerView.ItemDecoration {
    private lateinit var currentTitleView: View
    private var currentView: View? = null
    private val titleList: ArrayList<String>
    private val concreteData: Data

    constructor(concreteData: Data) {
        this.concreteData = concreteData
        titleList = concreteData.getTitles()
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state);
        var index = (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        var holder: RecyclerView.ViewHolder? = null
        parent.getChildAt(0)?.let {
            holder = parent.getChildViewHolder(it)
        }

        if (currentView == null) {
            currentView = LayoutInflater.from(parent.context).inflate(R.layout.title_item, parent, false)
        }
        currentTitleView = currentView!!
        currentTitleView.titleTextView.text = concreteData.getTitle(index)

        if (holder is VerticalAdapter.TitleHolder) {
            var titleHolder = holder as VerticalAdapter.TitleHolder
            val measureWidth = View.MeasureSpec.makeMeasureSpec(titleHolder.itemView.width, View.MeasureSpec.EXACTLY)
            val measuredHeight = View.MeasureSpec.makeMeasureSpec(titleHolder.itemView.height, View.MeasureSpec.EXACTLY)
            currentTitleView.measure(measureWidth, measuredHeight)
//            Log.d("WH", holder.itemView.width.toString() + "  " + holder.itemView.height.toString())
            currentTitleView.layout(0, 0, titleHolder.itemView.width, titleHolder.itemView.height)
            currentTitleView.draw(c!!)
        } else {
            val measureWidth = View.MeasureSpec.makeMeasureSpec(holder!!.itemView.width, View.MeasureSpec.EXACTLY)
            val measuredHeight = View.MeasureSpec.makeMeasureSpec(holder!!.itemView.height, View.MeasureSpec.EXACTLY)
            currentTitleView.measure(measureWidth, measuredHeight)
//            Log.d("WH  ", index.toString())
            var nextHolder = parent.findViewHolderForAdapterPosition(index + 1)
            if (nextHolder is VerticalAdapter.TitleHolder) {
                var textView = currentTitleView.titleTextView
                val bottomSpacing = currentTitleView.bottom - currentTitleView.titleTextView.bottom
                //
                Log.d("BottomSpacing ", currentTitleView.bottom.toString() + "  " + currentTitleView.titleTextView.bottom + " " + bottomSpacing.toString())

                val titleBottom = Math.min(holder!!.itemView.height, nextHolder.itemView.top)
                currentTitleView.layout(0, 0, holder!!.itemView.width, titleBottom)

                val textTopSpacing = textView.bottom - textView.top
                textView.bottom = titleBottom - bottomSpacing
                textView.top = textView.bottom - textTopSpacing
                if (currentTitleView.titleTextView.text.equals(nextHolder.textView.text)) {
                    val title = titleList.get(titleList.indexOf(currentTitleView.titleTextView.text!!) - 1)
                    currentTitleView.titleTextView.text = title
                }
                currentTitleView.draw(c!!)
            } else {
                currentTitleView.layout(0, 0, holder!!.itemView.width, holder!!.itemView.height)
                currentTitleView.draw(c!!)
            }
        }
    }
}