package com.techapp.james.cardlayoutdemo

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by James on 2018/3/6.
 */
class SpaceItemDecoration : RecyclerView.ItemDecoration {
    private var space: Int = 0

    constructor(space: Int) {
        this.space = space
    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {

        if (parent!!.getChildLayoutPosition(view) !== 0) {
            outRect!!.bottom = 3
        }
    }
}