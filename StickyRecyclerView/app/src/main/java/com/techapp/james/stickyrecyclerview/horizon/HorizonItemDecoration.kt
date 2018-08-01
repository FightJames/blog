package com.techapp.james.stickyrecyclerview.horizon

import android.graphics.Canvas
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.techapp.james.stickyrecyclerview.dataStructure.Data
import com.techapp.james.stickyrecyclerview.R
import com.techapp.james.stickyrecyclerview.vertical.VerticalAdapter
import kotlinx.android.synthetic.main.title_horizon.view.*

class HorizonItemDecoration : RecyclerView.ItemDecoration {

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
            currentView = LayoutInflater.from(parent.context).inflate(R.layout.title_horizon, parent, false)
        }
        currentTitleView = currentView!!
        currentTitleView.titleTextView.text = concreteData.getTitle(index)

        if (holder is HorizonAdapter.TitleHolder) {
            Log.d("Pass", " " + (holder as HorizonAdapter.TitleHolder).itemView.titleTextView.text)
            var titleHolder = holder as HorizonAdapter.TitleHolder
            val measureWidth = View.MeasureSpec.makeMeasureSpec(titleHolder.itemView.width, View.MeasureSpec.EXACTLY)
            val measuredHeight = View.MeasureSpec.makeMeasureSpec(titleHolder.itemView.height, View.MeasureSpec.EXACTLY)
            currentTitleView.measure(measureWidth, measuredHeight)
//            Log.d("WH", holder.itemView.width.toString() + "  " + holder.itemView.height.toString())
            currentTitleView.layout(0, 0, titleHolder.itemView.width, titleHolder.itemView.height)
            currentTitleView.draw(c!!)
        } else {
            val measureWidth = View.MeasureSpec.makeMeasureSpec(currentTitleView.width, View.MeasureSpec.EXACTLY)
            val measuredHeight = View.MeasureSpec.makeMeasureSpec(currentTitleView.height, View.MeasureSpec.EXACTLY)
            currentTitleView.measure(measureWidth, measuredHeight)
//            Log.d("WH  ", index.toString())
            var nextHolder = parent.findViewHolderForAdapterPosition(index + 1)
            if (nextHolder is HorizonAdapter.TitleHolder) {
                var textView = currentTitleView.titleTextView
                val rightSpacing = currentTitleView.right - currentTitleView.titleTextView.right
                //
                Log.d("RightSpacing ", currentTitleView.right.toString() + "  " + currentTitleView.titleTextView.right + " " + rightSpacing.toString())

                val titleRight = Math.min(holder!!.itemView.width, nextHolder.itemView.left)
                currentTitleView.layout(0, 0, titleRight, holder!!.itemView.height)

                val textLeftSpacing = textView.right - textView.left
                textView.right = titleRight - rightSpacing
                textView.left = textView.right - textLeftSpacing
                if (currentTitleView.titleTextView.text.equals(nextHolder.textView.text)) {
                    val title = titleList.get(titleList.indexOf(currentTitleView.titleTextView.text!!) - 1)
                    currentTitleView.titleTextView.text = title
                }
                currentTitleView.draw(c!!)
            } else {
                currentTitleView.layout(0, 0, currentTitleView.width, currentTitleView.height)
                currentTitleView.draw(c!!)
            }
        }
    }
}