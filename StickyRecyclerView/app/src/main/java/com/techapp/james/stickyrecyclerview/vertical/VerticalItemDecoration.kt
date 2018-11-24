package com.techapp.james.stickyrecyclerview.vertical

import android.graphics.Canvas
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techapp.james.stickyrecyclerview.R
import com.techapp.james.stickyrecyclerview.dataStructure.Data
import kotlinx.android.synthetic.main.title_item.view.*

class VerticalItemDecoration : RecyclerView.ItemDecoration {
    var concreteData: Data
    var currentView: View? = null
    var textViewHeight: Int = 0

    constructor(concreteData: Data) {
        this.concreteData = concreteData
    }

    var measureWidth: Int = 0
    var measuredHeight: Int = 0
    var bottomPadding: Int = 0
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state);
        var index = (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        var holder: RecyclerView.ViewHolder? = null
        parent.getChildAt(0)?.let {
            holder = parent.getChildViewHolder(it)
        }
        if (currentView == null) {
            currentView = LayoutInflater.from(parent.context).inflate(R.layout.title_item, parent, false)
            currentView!!.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        if (holder is VerticalAdapter.TitleHolder) {
            currentView!!.titleTextView.text = concreteData.getTitle(index)

            doMeasure(parent)

            Log.d("Parent Par", " Width ${parent.measuredWidth} Height ${parent.measuredHeight}")
            Log.d("Parent Par", " Current Width ${currentView!!.measuredWidth}  Current Height ${currentView!!.measuredHeight}")
            currentView!!.layout(0, 0, currentView!!.measuredWidth, currentView!!.measuredHeight)
            currentView!!.draw(c!!)

            bottomPadding = currentView!!.bottom - currentView!!.titleTextView.bottom
            textViewHeight = currentView!!.titleTextView.height
        } else {
            currentView!!.measure(measureWidth, measuredHeight)
//            Log.d("WH  ", index.toString())
            var nextHolder: RecyclerView.ViewHolder? = null
            nextHolder = parent.findViewHolderForAdapterPosition(index + 1)


            if (nextHolder is VerticalAdapter.TitleHolder) {
//                Log.d("RightTextSpacing ", currentTitleView.right.toString() + "  " + currentTitleView.titleTextView.right + " " + bottomPadding.toString())
                if (currentView!!.titleTextView.text.toString()
                        == nextHolder.textView.text.toString()) {
                    currentView!!.titleTextView.text = concreteData.getTitle(index)
                    doMeasure(parent)
                }

                var textView = currentView!!.titleTextView

                var titleBottom: Int
                titleBottom = Math.min(nextHolder!!.itemView.top, currentView!!.measuredHeight)


                currentView!!.layout(0, 0, currentView!!.measuredWidth, titleBottom)

//                val textLeftSpacing = textView.right - textView.left
                textView.bottom = titleBottom - bottomPadding
                textView.top = textView.bottom - textViewHeight
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
        measureWidth = View.MeasureSpec.makeMeasureSpec(parent.measuredWidth, View.MeasureSpec.EXACTLY)
        measuredHeight = View.MeasureSpec.makeMeasureSpec(parent.measuredHeight, View.MeasureSpec.AT_MOST)
        currentView!!.measure(measureWidth, measuredHeight)
        textViewHeight = currentView!!.titleTextView.measuredHeight
    }
}