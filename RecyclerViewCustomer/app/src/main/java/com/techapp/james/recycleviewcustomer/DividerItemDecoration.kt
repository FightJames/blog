package com.techapp.james.recycleviewcustomer

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.graphics.drawable.Drawable
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View


/**
 * Created by James on 2018/3/1.
 */
class DividerItemDecoration : RecyclerView.ItemDecoration {
    val ATTRS = intArrayOf(android.R.attr.listDivider)

    companion object {
        public val HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL

        public val VERTICAL_LIST = LinearLayoutManager.VERTICAL
    }


    private var mDivider: Drawable? = null

    private var mOrientation: Int = 0

    constructor(context: Context, orientation: Int) {
        var arr: TypedArray = context.obtainStyledAttributes(ATTRS)
        //Retrieve styled attribute information in this Context's theme

       println("Attr "+arr.getString(0))
        mDivider = arr.getDrawable(0)
        //Retrieve the Drawable for the attribute at index.
        arr.recycle()
        mOrientation = orientation
    }

    public fun setOrientation(orientation: Int) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw  IllegalArgumentException()
        }
        mOrientation = orientation
    }

    override fun onDraw(c: Canvas?, parent: RecyclerView?) {
        // println("Draw ")
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    public fun drawVertical(c: Canvas?, parent: RecyclerView?) {
        val left = parent!!.paddingLeft
        val right = parent!!.width - parent!!.paddingRight
        //val right = parent!!.width
        val childCount: Int = parent!!.childCount
        //println("DrawVertical")
        for (i in 0..childCount step 1) {

            var child: View? = parent.getChildAt(i)
            if (child != null) {
                val params: RecyclerView.LayoutParams = child.layoutParams as RecyclerView.LayoutParams
                val top = child.bottom + params.bottomMargin + Math.round(ViewCompat.getTranslationY(child))
                //up to down bound child.bottom
                val bottom = top + mDivider!!.intrinsicHeight
                mDivider!!.setBounds(left, top, right, bottom)
//                println("parent  "+parent!!.width+"  "+parent!!.paddingRight)
//                println("child bottom "+child.bottom+" params bottomMargin "+params.bottomMargin+" round "+Math.round(ViewCompat.getTranslationY(child)))
//                println("Drawable Bound "+mDivider!!.intrinsicHeight)
//                //drawable set in drawable file's item_line.xml
//                println("Bounds "+left+"  "+top+"  "+right+"  "+bottom)
                mDivider!!.draw(c)
//                mDivider!!.setBounds(0, child.top, mDivider!!.intrinsicHeight, bottom)
//                mDivider!!.draw(c)
//                mDivider!!.setBounds(0,child.top,mDivider!!.intrinsicWidth,child.top-mDivider!!.intrinsicHeight)
//                mDivider!!.draw(c)
//                mDivider!!.setBounds(child.right-mDivider!!.intrinsicHeight,child.top,child.right,child.bottom)
//                mDivider!!.draw(c)
            }
        }
    }

    fun drawHorizontal(c: Canvas?, parent: RecyclerView?) {
        val top = parent!!.paddingTop
        val bottom = parent.height - parent.paddingBottom

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child
                    .layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin +
                    Math.round(ViewCompat.getTranslationX(child))
            val right = left + mDivider!!.getIntrinsicHeight()
            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(c)
        }
    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        if (mOrientation === VERTICAL_LIST) {
            println("recycle")
            outRect!!.set(0, mDivider!!.intrinsicWidth, 0, mDivider!!.getIntrinsicHeight())
            //mDivider.getIntrinsicHeight return drawable's height
        } else {
            println("recycle")
            outRect!!.set(0, 0, mDivider!!.getIntrinsicWidth(), 0)
            //mDivider.getIntrinsicHeight return drawable's height
            //octRect.set Set the rectangle's coordinates to the specified values.
        }
        //super.getItemOffsets(outRect, view, parent, state)
    }
}