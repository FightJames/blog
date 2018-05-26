package com.techapp.james.musicdemo.view.fragmentPage.albumFragment

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import com.techapp.james.musicdemo.R
import com.techapp.james.musicdemo.model.musicModel.ManageCurrentPlaySongList
import com.techapp.james.musicdemo.model.musicModel.PlayList
import com.techapp.james.musicdemo.model.playManager.ExoPlayerManager
import com.techapp.james.musicdemo.view.fragmentPage.firstFragment.SongRecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.view.*

/**
 * Created by James on 2018/3/24.
 */
class albumListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private var context: Context
    private var data: PlayList
    private var lastHolder: ItemViewHolder? = null
    private var FIRSTLAYOUT = 0
    private var ITEMLAYOUT = 1;
    private var holderList: ArrayList<ItemViewHolder>
    private var currentPosition: Int = 0;
    constructor(context: Context, playList: PlayList) {
        this.context = context
        this.data = playList
        holderList = ArrayList()
    }
    fun setColorInListItem() {
        for (i in 0..(holderList.size - 1))
            if (ManageCurrentPlaySongList.getCurrentSongName().equals(holderList[i].itemView.songTextView.text)) {
                recoverLastHolderColor()
                holderList[i].itemView.songTextView.setTextColor(Color.GREEN)
                lastHolder = holderList[i]
            }
    }
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var view: View? = null
        if(currentPosition>(holderList.size - 1)) {
            when (viewType) {
                FIRSTLAYOUT -> {
                    view = LayoutInflater.from(context).inflate(R.layout.album_fragment_recycler_first_item, parent, false)
                }
                ITEMLAYOUT -> {
                    view = LayoutInflater.from(context).inflate(R.layout.first_fragment_songlist_recyclerview_item_layout, parent, false)
                }
            }
            var itemHolder = ItemViewHolder(view)
            holderList.add(itemHolder)
        }
        return holderList[currentPosition]
    }
    private fun recoverLastHolderColor(){
        if (lastHolder != null) {
            lastHolder!!.itemView.songTextView.setTextColor(Color.WHITE)
        }
    }
    override fun getItemViewType(position: Int): Int {
        currentPosition=position
        if (position == FIRSTLAYOUT) {
            return FIRSTLAYOUT
        }
        return ITEMLAYOUT
    }

    override fun getItemCount(): Int {
        return data.list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as ItemViewHolder).songTextView.text = data.list[position].title
        (holder as ItemViewHolder).ectImageView.setOnClickListener {
            var popup = PopupMenu(context, holder.ectImageView)
            popup.menuInflater.inflate(R.menu.album_fragment_recycler_menu, popup.menu)
            popup.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.removeToPlayList -> {
                        var song = data.list[position]
                        data.list.remove(song)
                        this.notifyDataSetChanged()
                    }
                }
                true
            }
            popup.show()
        }
        if (ManageCurrentPlaySongList.getCurrentSongName().equals(data.list[position].title)) {
            lastHolder = holder
            holder.itemView.songTextView.setTextColor(Color.GREEN)
        }
        holder.itemView.setOnClickListener {
            if (lastHolder != null) {
                lastHolder!!.itemView.songTextView.setTextColor(Color.WHITE)
            }
            ManageCurrentPlaySongList.playList = data
            ExoPlayerManager.startPlay(data.list[position].source, true)
            ManageCurrentPlaySongList.setCurrentIndex(position)
            holder.itemView.songTextView.setTextColor(Color.GREEN)
            lastHolder = holder
        }
    }
}