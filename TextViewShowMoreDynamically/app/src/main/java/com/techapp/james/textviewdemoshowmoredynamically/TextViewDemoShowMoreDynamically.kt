package com.techapp.james.textviewdemoshowmoredynamically

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_text_view_demo_show_more_dynamically.*

class TextViewDemoShowMoreDynamically : AppCompatActivity() {
    var textView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_view_demo_show_more_dynamically)
        var text=""
        for(i in 0..50){
            text+="James is here "
        }
        textView = TextView(this)
        textView!!.text=text

        textView!!.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, resources.displayMetrics))
        textView!!.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40f, resources.displayMetrics).toInt()
        root.addView(textView)

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        limitTextView(textView!!.text.toString(),textView,null,2)
        println("Text View width "+textView!!.width)
    }

    var staticLayout: StaticLayout?=null
    fun getLastCharIndexForLimitTextView(textView: TextView, content: String, width: Int, maxLine: Int): Int {
        println("String's width: " + width)
        var textPaint: TextPaint = textView.getPaint();
        staticLayout = StaticLayout(content, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false);
        //StaticLayout("需要分行的字串","畫筆目標","Layout寬度","
        // layout的對齊方式，ALIGN_CENTER， ALIGN_NORMAL， ALIGN_OPPOSITE",
        // "相對行間距，相對字體大小，1.5f表示行間距為1.5倍的字體高度",
        // "在基礎行距上加多少 實際行間距等於這兩者的和","是否包含padding")
        if (staticLayout!!.lineCount > maxLine) {
            return staticLayout!!.getLineStart(maxLine)
            //Return the text offset(游標) of the beginning of the specified line
        }
        return -1
    }
    fun limitTextView(originData: String, textView: TextView?, clickListener: View.OnClickListener?, maxLine: Int) {
        if (textView == null) {
            return
        }
        val showmore = "...show more"
        var width = textView.width//取得textView的寬度
        textView.measuredWidth
       // println("TextView's width " + width)
        var lastCharIndex = getLastCharIndexForLimitTextView(textView, originData, width, maxLine)
        // println("LastCharIndex " + lastCharIndex + " sumIdx " + originData[lastCharIndex])
        if (lastCharIndex < 0) {//如果行數沒超過限制
            textView.text = originData
            return
        }
        //如果超出了行數限制
        textView.movementMethod = LinkMovementMethod.getInstance()//this will deprive the recyclerView's focus

        var explicitText: String? = ""
        if (originData[lastCharIndex] == '\n') {//manual enter
            explicitText = originData.substring(0, lastCharIndex)
        } else if (lastCharIndex > 12) {//TextView auto enter
            explicitText = originData.substring(0, lastCharIndex - showmore.length)
        }
        val sourceLength = explicitText!!.length

        explicitText = "$explicitText$showmore"
        val mSpan = SpannableString(explicitText)
        mSpan.setSpan(object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = textView.textColors.defaultColor //default text color
                //it will make show more tag Red
                ds.isAntiAlias = true  //抗鋸齒
                ds.isUnderlineText = false //show more under line
            }

            override fun onClick(widget: View) {//"...show more" click event
                println("click showmore textView Height "+textView.height)
                textView.text = originData
                textView.setOnClickListener(null)
                //   textView.height=300
                println("click showmore textView Height "+textView.height)
                changeTextViewHeight()
                Handler().postDelayed(Runnable {
                    //UI thread
                    if (clickListener != null)
                        textView.setOnClickListener(clickListener)//prevent the double click
                }, 20)
            }
        }, sourceLength, explicitText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        //sourceLength 沒有Show more
        // explicitText.length有Show more
        //這樣設定是為了把show more消除

        textView.text = mSpan

        /**
         * setSpan(Object what, int start, int end, int flags)
        Attach the specified markup object to the range start…end of the text, or move the object to that range if it was already attached elsewhere.
         * **/
//        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE --- 不包含start和end所在的端點        (a,b)
//        Spanned.SPAN_EXCLUSIVE_INCLUSIVE --- 不包含端start，但包含end所在的端点     (a,b]
//        Spanned.SPAN_INCLUSIVE_EXCLUSIVE --- 包含start，但不包含end所在的端点   [a,b)
//        Spanned.SPAN_INCLUSIVE_INCLUSIVE --- 包含start和end所在的端点          [a,b]

    }
    fun changeTextViewHeight(){
    var height=staticLayout!!.lineCount*TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, resources.displayMetrics).toInt()
        println("Line count "+staticLayout!!.lineCount+" height "+height)

        textView!!.height= staticLayout!!.lineCount*TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18f, resources.displayMetrics).toInt()
    }
}