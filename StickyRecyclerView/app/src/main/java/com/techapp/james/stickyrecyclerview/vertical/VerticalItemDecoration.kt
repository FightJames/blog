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
    private val titleData = HashMap<String, Int>()

    constructor(concreteData: Data) {
        this.concreteData = concreteData
        titleList = concreteData.getTitles()
    }

    fun putTitleData(title: String, width: Int) {
        titleData.put(title, width)
    }

    var textHeight: Int = 0
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
        }
        currentTitleView = currentView!!
        currentTitleView.titleTextView.text = concreteData.getTitle(index)

        if (holder is VerticalAdapter.TitleHolder) {
            var titleHolder = holder as VerticalAdapter.TitleHolder
            //solve problem in adapter
            titleData.put(titleHolder.textView.text.toString(), titleHolder.itemView.height)

            val measureWidth = View.MeasureSpec.makeMeasureSpec(titleHolder.itemView.width, View.MeasureSpec.EXACTLY)
            val measuredHeight = View.MeasureSpec.makeMeasureSpec(titleHolder.itemView.height, View.MeasureSpec.EXACTLY)
            textHeight = currentView!!.titleTextView.measuredHeight

            bottomPadding = currentTitleView.measuredHeight - currentTitleView.titleTextView.bottom
            currentTitleView.measure(measureWidth, measuredHeight)
            currentTitleView.layout(0, 0, titleHolder.itemView.width, titleHolder.itemView.height)
            currentTitleView.draw(c!!)
        } else {
            val measureWidth = View.MeasureSpec.makeMeasureSpec(currentTitleView.width, View.MeasureSpec.EXACTLY)
            val measuredHeight = View.MeasureSpec.makeMeasureSpec(currentTitleView.height, View.MeasureSpec.EXACTLY)
            currentTitleView.measure(measureWidth, measuredHeight)
//            Log.d("WH  ", index.toString())
            var nextHolder: RecyclerView.ViewHolder? = null
            for (i in 1..10) {
                nextHolder = parent.findViewHolderForAdapterPosition(index + i)
                if (nextHolder is VerticalAdapter.TitleHolder) {
                    break
                }
            }
            if (nextHolder is VerticalAdapter.TitleHolder) {
                var textView = currentTitleView.titleTextView
//                Log.d("RightTextPadding ", currentTitleView.right.toString() + "  " + currentTitleView.titleTextView.right + " " + bottomPadding.toString())
                val viewHeigh = titleData.get(currentTitleView.titleTextView.text.toString())!!
                var titleBottom = Math.min(nextHolder!!.itemView.top, viewHeigh)
                currentTitleView.layout(0, 0, holder!!.itemView.width, titleBottom)
                textView.bottom = titleBottom - bottomPadding
                textView.top = textView.bottom - textHeight
                Log.d("textViewHeight ", textHeight.toString())
                getLastTitle(nextHolder)
                currentTitleView.draw(c!!)
            } else {
                var heigh = titleData.get(currentTitleView.titleTextView.text.toString())
                var titleString = currentTitleView.titleTextView.text.toString()
                Log.d("CurrentTitleView ", "$titleString $heigh")
                if (heigh != null && heigh != 0) {
                    currentTitleView.layout(0, 0, currentTitleView.width, heigh)
                } else {
                    currentTitleView.layout(0, 0, currentTitleView.width, currentTitleView.height)
                }
                currentTitleView.draw(c!!)
            }
        }
    }

    private fun getLastTitle(nextHolder: VerticalAdapter.TitleHolder) {
        if (currentTitleView.titleTextView.text.equals(nextHolder.textView.text)) {
            val title = titleList.get(titleList.indexOf(currentTitleView.titleTextView.text!!) - 1)
            currentTitleView.titleTextView.text = title
            val measureWidth = View.MeasureSpec.makeMeasureSpec(currentTitleView.titleTextView.width, View.MeasureSpec.EXACTLY)
            val measuredHeight = View.MeasureSpec.makeMeasureSpec(currentTitleView.titleTextView.height, View.MeasureSpec.EXACTLY)
            currentTitleView.titleTextView.measure(measureWidth, measuredHeight)
            textHeight = currentTitleView.titleTextView.measuredHeight
        }
    }
}