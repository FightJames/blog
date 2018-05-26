package com.techapp.james.iguidemo.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.techapp.james.iguidemo.TextViewCustomer.CustomerTextView
import kotlinx.android.synthetic.main.item_layout.view.*
import org.w3c.dom.Text

/**
 * Created by James on 2018/3/12.
 */
class ItemHolder : RecyclerView.ViewHolder, SetData {
    var profilePicture: ImageView
    var contentImageView: ImageView
    var loveImageBtn: ImageButton
    var commonImageBtn: ImageButton
    var shareImageBtn: ImageButton
    var contentLayout: ConstraintLayout
    var contentData: Any? = null
    var context: Context
    var contentTextView: TextView?=null
    var nameTextView: TextView?=null
    override fun setData(data: Any) {
        this.contentData = data
    }

    constructor(itemView: View?, context: Context) : super(itemView) {
        profilePicture = itemView!!.profilePicture
        contentImageView = itemView!!.contentImageView
        loveImageBtn = itemView!!.loveImageButton
        commonImageBtn = itemView!!.commonImageButton
        shareImageBtn = itemView!!.shareImageButton
        contentLayout = itemView!!.contentLayput
        nameTextView=itemView!!.nameTextView
        nameTextView!!.text="James"
        this.context = context
    }

    fun limitText() {
        nameTextView = TextView(context)
        contentTextView = CustomerTextView(context, 2)
        contentTextView!!.width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 380f, context.resources.displayMetrics).toInt()
        contentTextView!!.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40f, context.resources.displayMetrics).toInt()
        contentLayout.addView(nameTextView)
        contentLayout.addView(contentTextView)

        nameTextView!!.text = "James"
        nameTextView!!.setTextColor(Color.BLACK)
        //  nameTextView.setBackgroundColor(Color.BLACK)
        nameTextView!!.setTypeface(Typeface.DEFAULT, Typeface.BOLD)
        var nameSpace = ""
        for (i in 0..nameTextView!!.text.length) {
            nameSpace += "  "
        }
        var contentData = nameSpace + " Content Content Content Content Content Content Content Content Content"
       for(i in 0..50){
           contentData+=" ItemHolder"
       }
        (contentTextView!! as CustomerTextView).setContent(contentData)
        contentTextView!!.setTextColor(Color.BLACK)
        //(contentTextView as CustomerTextView).reset()
    }
}