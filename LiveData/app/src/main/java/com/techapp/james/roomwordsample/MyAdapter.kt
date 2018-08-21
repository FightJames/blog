package com.techapp.james.roomwordsample

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.techapp.james.roomwordsample.data.Word
import kotlinx.android.synthetic.main.item.view.*

class MyAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private var mWords: List<Word>? = null

    constructor()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var v = LayoutInflater.from(parent!!.context).inflate(R.layout.item, parent, false)
        return MyHolder(v)
    }

    override fun getItemCount(): Int {
        if (mWords != null) {
            return mWords!!.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as MyHolder
        mWords?.let {
            holder.textView.text = mWords!![position].mWord
        }
    }

    fun setWords(words: List<Word>) {
        mWords = words
        notifyDataSetChanged()
    }

    class MyHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView!!.textView
    }
}