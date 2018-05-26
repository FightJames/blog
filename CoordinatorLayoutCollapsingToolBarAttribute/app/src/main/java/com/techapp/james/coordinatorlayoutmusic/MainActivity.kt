package com.techapp.james.coordinatorlayoutmusic

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
//    , AppBarLayout.OnOffsetChangedListener
//    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
//        if ((verticalOffset*-1) == appBarLayout!!.totalScrollRange) {
//            titleTextView.text = "James"
//        } else {
//            titleTextView.text = ""
//        }
//        println("verticalOffset  " + verticalOffset + " appBarLayout!!.totalScrollRange  " + appBarLayout!!.totalScrollRange)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //appbar_layout.addOnOffsetChangedListener(this)
//        button.setOnTouchListener(object: View.OnTouchListener{
//            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//            when(event!!.getAction()){
//                MotionEvent.ACTION_MOVE->{
//                    //v!!.setX(event!!.getRawX()-v!!.width/2)
//                    v!!.setY(event!!.getRawY()-v!!.height/2)
//                }
//            }
//                return false;
//            }
//
//        })
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyAdapter(this)

    }
}
