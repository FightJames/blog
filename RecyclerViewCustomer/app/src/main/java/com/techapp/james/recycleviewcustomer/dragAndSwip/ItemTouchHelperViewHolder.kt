package com.techapp.james.recycleviewcustomer.dragAndSwip

interface ItemTouchHelperViewHolder {
    // it called every time when thie state of a View Holder changes to drag(ACTION_STATE_DRAG) or swip(ACTION_STATE_SWIPE)
    fun onItemSelected()

    //it call whan dragView is dropped or swip is cancelled or completed
    fun onItemClear()
}