package com.techapp.james.mediasession

import android.content.Context
import android.support.v4.media.MediaBrowserCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.song_item.view.*

class MusicListAdapter(private val context: Context, private val list: List<MediaBrowserCompat.MediaItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mOnItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    fun setOnItemClickListener(mOnItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemViewId = R.layout.song_item
        return ViewHolder(LayoutInflater.from(context).inflate(itemViewId, parent, false))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        holder.textTitle.text = list[position].description.title

        // onItemClickListener
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener {
                val pos = holder.layoutPosition
                mOnItemClickListener!!.onItemClick(holder.itemView, pos)
            }

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder : RecyclerView.ViewHolder {
        lateinit var textTitle: TextView

        constructor(itemView: View) : super(itemView) {
            textTitle = itemView.textTitle
        }
    }
}