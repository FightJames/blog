package com.techapp.james.mediasession

import android.content.Context
import android.media.browse.MediaBrowser
import android.support.v4.media.MediaBrowserCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.song_item.view.*

class MusicListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    val data: ArrayList<MediaBrowserCompat.MediaItem>
    val layoutInflater: LayoutInflater
    var mOnItemClickListener: OnItemClickListener? = null

    constructor(context: Context, data: ArrayList<MediaBrowserCompat.MediaItem>) {
        this.layoutInflater = LayoutInflater.from(context)
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = layoutInflater.inflate(R.layout.song_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ItemViewHolder
        holder.songTextView.text = data[position].description.title
        mOnItemClickListener?.let {
            holder.itemView.setOnClickListener {
                mOnItemClickListener?.onItemClick(it, position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    fun setOnItemClickListener(mOnItemClickListener: OnItemClickListener) {

    }

    class ItemViewHolder : RecyclerView.ViewHolder {
        val songTextView: TextView

        constructor(itemView: View) : super(itemView) {
            songTextView = itemView.songTextView
        }
    }

}