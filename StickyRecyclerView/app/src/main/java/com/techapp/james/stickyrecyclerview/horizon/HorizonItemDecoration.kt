package com.techapp.james.stickyrecyclerview.horizon

import android.graphics.Canvas
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techapp.james.stickyrecyclerview.R
import com.techapp.james.stickyrecyclerview.dataStructure.Data
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.android.synthetic.main.title_horizon.view.*

class HorizonItemDecoration : RecyclerView.ItemDecoration {
    var concreteData: Data
    var currentView: View? = null
    var textViewWidth: Int = 0

    constructor(concreteData: Data) {
        this.concreteData = concreteData
    }

    var measureWidth: Int = 0
    var measuredHeight: Int = 0
    var rightPadding: Int = 0
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state);
        var index = (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        var holder: RecyclerView.ViewHolder? = null
        parent.getChildAt(0)?.let {
            holder = parent.getChildViewHolder(it)
        }
        if (currentView == null) {
            currentView = LayoutInflater.from(parent.context).inflate(R.layout.title_horizon, parent, false)
            currentView!!.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }

        if (holder is HorizonAdapter.TitleHolder) {
            currentView!!.titleTextView.text = concreteData.getTitle(index)

            doMeasure(parent)
            Log.d("Parent Par", " Width ${parent.measuredWidth} Height ${parent.measuredHeight}")
            Log.d("Parent Par", " Current Width ${currentView!!.measuredWidth}  Current Height ${currentView!!.measuredHeight}")
            currentView!!.layout(0, 0, currentView!!.measuredWidth, currentView!!.measuredHeight)
            currentView!!.draw(c!!)

            rightPadding = currentView!!.right - currentView!!.titleTextView.right
            textViewWidth = currentView!!.titleTextView.width
        } else {
            currentView!!.measure(measureWidth, measuredHeight)
//            Log.d("WH  ", index.toString())
            var nextHolder: RecyclerView.ViewHolder? = null
            nextHolder = parent.findViewHolderForAdapterPosition(index + 1)


            if (nextHolder is HorizonAdapter.TitleHolder) {
//                Log.d("RightTextSpacing ", currentTitleView.right.toString() + "  " + currentTitleView.titleTextView.right + " " + rightPadding.toString())
                if (currentView!!.titleTextView.text.toString()
                        == nextHolder.textView.text.toString()) {
                    currentView!!.titleTextView.text = concreteData.getTitle(index)
                    doMeasure(parent)
                }

                var textView = currentView!!.titleTextView

                var titleRight: Int
                titleRight = Math.min(nextHolder!!.itemView.left, currentView!!.measuredWidth)


                currentView!!.layout(0, 0, titleRight, currentView!!.measuredHeight)

//                val textLeftSpacing = textView.right - textView.left
                textView.right = titleRight - rightPadding
                textView.left = textView.right - textViewWidth
                currentView!!.draw(c!!)
            } else {
                currentView!!.titleTextView.text = concreteData.getTitle(index)
                doMeasure(parent)
                currentView!!.layout(0, 0, currentView!!.measuredWidth, currentView!!.measuredHeight)
                currentView!!.draw(c!!)
            }
        }
    }


    private fun doMeasure(parent: RecyclerView) {
        measureWidth = View.MeasureSpec.makeMeasureSpec(parent.measuredWidth, View.MeasureSpec.AT_MOST)
        measuredHeight = View.MeasureSpec.makeMeasureSpec(parent.measuredHeight, View.MeasureSpec.EXACTLY)
        currentView!!.measure(measureWidth, measuredHeight)
        textViewWidth = currentView!!.titleTextView.measuredWidth
    }
}