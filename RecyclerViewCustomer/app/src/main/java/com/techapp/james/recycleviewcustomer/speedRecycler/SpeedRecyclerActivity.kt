package com.techapp.james.recycleviewcustomer.speedRecycler

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import com.techapp.james.recycleviewcustomer.R
import kotlinx.android.synthetic.main.activity_speed_recycler.*

class SpeedRecyclerActivity : AppCompatActivity() {
    var x = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speed_recycler)
        var layoutManager = LinearLayoutManager(this)
        layoutManager.orientation =
                LinearLayout.HORIZONTAL
        recyclerView.layoutManager = layoutManager
        var list = ArrayList<String>()
        for (i in 0..100) {
            list.add(i.toString())
        }
        //dp to px  every item width is 96 dp. so move every item
        // 96 dp.
        isRecyclerViewTouch(false)
        var px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 96f, getResources().getDisplayMetrics());

        recyclerView.adapter = SpeedAdapter(this, list)
        moveBtn.setOnClickListener {
            //            recyclerView.scrollTo(x, 0)
//            for (i in 0..100) {
            var dis: Int = (10 * px).toInt()
            recyclerView.smoothScrollBy(dis, 0, DecelerateInterpolator())
//            }
//            x += 100
            Log.d("click ", "pass")
        }
    }

    private fun isRecyclerViewTouch(isTouch: Boolean) {
        if (isTouch) {
            textView2.visibility=View.GONE
        } else {
            textView2.setOnClickListener { true }
        }
    }
}
