package com.techapp.james.stickyrecyclerview

import android.content.Context
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.techapp.james.stickyrecyclerview.dataStructure.ConcreteData
import com.techapp.james.stickyrecyclerview.dataStructure.Data
import com.techapp.james.stickyrecyclerview.horizon.HorizonAdapter
import com.techapp.james.stickyrecyclerview.horizon.HorizonItemDecoration
import com.techapp.james.stickyrecyclerview.vertical.VerticalAdapter
import com.techapp.james.stickyrecyclerview.vertical.VerticalItemDecoration


class StickyRecyclerView : RecyclerView {
    var data: Data = ConcreteData()
    var isEnableDivider = true
    var isVertical = true

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.StickyRecyclerView,
                0, 0)
        isEnableDivider = a.getBoolean(R.styleable.StickyRecyclerView_enableDivider, true)

        isVertical = a.getBoolean(R.styleable.StickyRecyclerView_isVertical, true)
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.StickyRecyclerView,
                0, 0)
        isEnableDivider = a.getBoolean(R.styleable.StickyRecyclerView_enableDivider, true)

        isVertical = a.getBoolean(R.styleable.StickyRecyclerView_isVertical, true)
        init()
    }

    private fun init() {
        if (isVertical) {
            layoutManager = LinearLayoutManager(this.context)
            var verticalAdapter = VerticalAdapter(data)
            var verticalItemDecoration = VerticalItemDecoration(data)
//            verticalAdapter.sendTitleDecoration = verticalItemDecoration::putTitleData
            adapter = verticalAdapter
            addItemDecoration(verticalItemDecoration)
        } else {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            var horizonItemDecoration = HorizonItemDecoration(data)
            addItemDecoration(horizonItemDecoration)
            var horizonAdapter = HorizonAdapter(data)
            adapter = horizonAdapter
//            horizonAdapter.sendTitleDecoration = horizonItemDecoration::putTitleData

        }
        if (isVerticalDivider()) {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        } else if (isHorizonDivider()) {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
        }
    }

    private fun isVerticalDivider(): Boolean {
        return isEnableDivider && isVertical
    }

    private fun isHorizonDivider(): Boolean {
        return isEnableDivider && (!isVertical)
    }

    fun insertOrUpdate(title: String, item: String) {
        data.insertOrUpdate(title, item)
    }

    fun remove(pos: Int): Boolean {
        return data.remove(pos)
    }

    fun getItem(pos: Int): String {
        return data.getItem(pos)
    }

}