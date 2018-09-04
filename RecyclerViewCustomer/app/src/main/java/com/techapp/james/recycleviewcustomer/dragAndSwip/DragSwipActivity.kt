package com.techapp.james.recycleviewcustomer.dragAndSwip

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import com.techapp.james.recycleviewcustomer.R
import kotlinx.android.synthetic.main.activity_drag_swip.*

class DragSwipActivity : AppCompatActivity() {
    val list = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drag_swip)

        val s = "abcdesdlgjljgdlj"
        s.forEach { e -> list.add(e.toString()) }
        var myAdapter = MyAdapter(list)
        recyclerList.adapter = myAdapter
        recyclerList.layoutManager = LinearLayoutManager(this)


        var touchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
                // https://developer.android.com/reference/android/support/v7/widget/helper/ItemTouchHelper.Callback.html#getMovementFlags(android.support.v7.widget.RecyclerView,%20android.support.v7.widget.RecyclerView.ViewHolder)
                var dragFlag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                var swipeFlag = ItemTouchHelper.START or ItemTouchHelper.END
                return makeMovementFlags(dragFlag, swipeFlag)
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                myAdapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
//                Log.d("ItemTouch", "From ${viewHolder.adapterPosition} Target ${target.adapterPosition}")
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                myAdapter.onItemDismiss(viewHolder.adapterPosition)
            }

            override fun isLongPressDragEnabled(): Boolean {
                return true
            }

            override fun isItemViewSwipeEnabled(): Boolean {
                return true
            }

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
                //only active item to change background
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    if (viewHolder is ItemTouchHelperViewHolder) {
                        viewHolder.onItemSelected()
                    }
                }
            }

            override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, viewHolder)
                if (viewHolder is ItemTouchHelperViewHolder) {
                    viewHolder.onItemClear()
                }
            }
        })
        myAdapter.dragFunction = { viewHolder ->
            touchHelper.startDrag(viewHolder)
        }
        touchHelper.attachToRecyclerView(recyclerList)
    }
}
