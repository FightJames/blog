package com.techapp.james.expandablelistviewdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var mainItem = arrayOf("語言", "資訊")
    var secondItem = arrayOf(arrayOf("英文", "國文"), arrayOf("SQL", "Mongo"))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var iconOnClickListener = object : View.OnClickListener {
            override fun onClick(v: View) {
                //获取被点击图标所在的group的索引
                val map = v.getTag() as Map<String, Any>
                val groupPosition = map["groupPosition"] as Int
                //                boolean isExpand = (boolean) map.get("isExpanded");   //这种是通过tag传值
                val isExpand = expandableListView.isGroupExpanded(groupPosition)    //判断分组是否展开

                if (isExpand) {
                    expandableListView.collapseGroup(groupPosition)
                } else {
                    expandableListView.expandGroup(groupPosition)
                }
            }
        }
        val myAdapter = MyAdapter(this.applicationContext, mainItem, secondItem,iconOnClickListener)
        expandableListView.setAdapter(myAdapter)
        expandableListView.expandGroup(0)
    }
    //       expandListView.collapseGroup()
//        收起position的group
//       expandListView.expandGroup()
//       展開position的group
    //expandListView.isGroupExpanded()
    //判斷position的Group是否展開
    //expandListView.setOnChildClickListener()
    //為子項目設定OnclickListener
    //expandListView.setOnGroupExpandListener()
    //Gruop展開設置Listener
}
