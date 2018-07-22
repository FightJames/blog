package com.techapp.james.stickyrecyclerview

import android.graphics.Canvas
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.title_item.view.*


class ItemDecoration : RecyclerView.ItemDecoration() {
    val TAG = "ItemDecoration"
    private lateinit var currentTitle: View
    private var preBottom: Int = 0
    private var current: View? = null
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state);
        var index = (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        Log.d(TAG, "onDrawOver index " + index)

        var holder = parent.findViewHolderForAdapterPosition(index)
//link list 效率
        if (holder is MyAdapter.TitleHolder) {
            if (current == null) {
                current = LayoutInflater.from(parent.context).inflate(R.layout.title_item, parent, false)
            }
            currentTitle = current!!

            currentTitle.titleTextView.text = holder .textView.text
            val measureWidth = View.MeasureSpec.makeMeasureSpec(holder.itemView.width, View.MeasureSpec.EXACTLY)
            val measuredHeight = View.MeasureSpec.makeMeasureSpec(holder.itemView.height, View.MeasureSpec.EXACTLY)
            currentTitle.measure(measureWidth, measuredHeight)
            Log.d("WH", holder.itemView.width.toString() + "  " + holder.itemView.height.toString())
            currentTitle.layout(0, 0, holder.itemView.width, holder.itemView.height)
            currentTitle.draw(c!!)
        } else {
            val measureWidth = View.MeasureSpec.makeMeasureSpec(holder.itemView.width, View.MeasureSpec.EXACTLY)
            val measuredHeight = View.MeasureSpec.makeMeasureSpec(holder.itemView.height, View.MeasureSpec.EXACTLY)
            currentTitle.measure(measureWidth, measuredHeight)
            Log.d("WH", holder.itemView.width.toString() + "  " + holder.itemView.height.toString())
            var nextHolder = parent.findViewHolderForAdapterPosition(index + 1)
            if (nextHolder is MyAdapter.TitleHolder) {
                val bottom = Math.min(holder.itemView.height, nextHolder.itemView.top)
                currentTitle.layout(0, 0, holder.itemView.width, bottom)
                if (preBottom != bottom) {
                    var textView = currentTitle.titleTextView
                    textView.bottom -= (holder.itemView.height - nextHolder.itemView.top)
                    textView.top -= (holder.itemView.height - nextHolder.itemView.top)
                }
                currentTitle.draw(c!!)
                preBottom = bottom
            } else {
                currentTitle.layout(0, 0, holder.itemView.width, holder.itemView.height)
                currentTitle.draw(c!!)
            }
        }
    }
}
