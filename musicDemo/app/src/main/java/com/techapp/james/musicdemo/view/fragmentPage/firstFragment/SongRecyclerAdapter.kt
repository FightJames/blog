package com.techapp.james.musicdemo.view.fragmentPage.firstFragment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.FragmentManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import com.techapp.james.musicdemo.R

import com.techapp.james.musicdemo.model.musicModel.ManageCurrentPlaySongList
import com.techapp.james.musicdemo.model.musicModel.PlayList
import com.techapp.james.musicdemo.model.playManager.ExoPlayerManager
import com.techapp.james.musicdemo.view.choosePlayListView.ChoosePlayListActivity
import kotlinx.android.synthetic.main.activity_main.view.*

/**
 * Created by James on 2018/3/19.
 */
class SongRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private var data: PlayList
    private var context: Context
    private var lastHolder: ItemHolder? = null
    private var fragmentManager: FragmentManager
    private var holderList: ArrayList<ItemHolder>
    private var currentPosition: Int = 0;

    constructor(data: PlayList, context: Context, fragmentManager: FragmentManager) {
        this.data = data
        this.context = context
        this.fragmentManager = fragmentManager
        holderList = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        if (currentPosition > (holderList.size - 1)) {
            var view = LayoutInflater.from(context).inflate(R.layout.first_fragment_songlist_recyclerview_item_layout, parent, false)
            var itemHolder = ItemHolder(view)
            holderList.add(itemHolder)
        }
        return holderList.get(currentPosition)
    }

    fun setColorInListItem() {
        for (i in 0..(holderList.size - 1))
            if (ManageCurrentPlaySongList.getCurrentSongName().equals(holderList[i].itemView.songTextView.text)) {
                recoverLastHolderColor()
                holderList[i].itemView.songTextView.setTextColor(Color.GREEN)
                lastHolder = holderList[i]
            }
    }

    override fun getItemCount(): Int {
        return data.list.size
    }

    override fun getItemViewType(position: Int): Int {
        currentPosition = position
        return currentPosition
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder!! as ItemHolder).songItem.text = data.list[position].title
//        holder.itemView.setOnLongClickListener {
//            true
//        }
        (holder as ItemHolder).ectImageView.setOnClickListener {
            var popup = PopupMenu(context, holder.ectImageView)
            popup.menuInflater.inflate(R.menu.first_fragment_item_menu, popup.menu)
            popup.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.addToPlayList -> {
                        var i = Intent()
                        i.setClass(context, ChoosePlayListActivity::class.java)
                        PassConfigure.passSong = data.list[position]
                        context.startActivity(i)
                    }
                }
                true
            }
            popup.show()
        }
        holder.itemView.setOnClickListener {
            ManageCurrentPlaySongList.playList = data
            ExoPlayerManager.startPlay(data.list[position].source, true)
            recoverLastHolderColor()
            ManageCurrentPlaySongList.setCurrentIndex(position)
            ManageCurrentPlaySongList.notifyCurrentSongNameAndPlayBtn()
            holder.itemView.songTextView.setTextColor(Color.GREEN)
            lastHolder = holder as ItemHolder
        }
        if (ManageCurrentPlaySongList.getCurrentSongName().equals(holder.itemView.songTextView.text)) {
            holder.itemView.songTextView.setTextColor(Color.GREEN)
            lastHolder = holder as ItemHolder
        }
    }
    private fun recoverLastHolderColor(){
        if (lastHolder != null) {
            lastHolder!!.itemView.songTextView.setTextColor(Color.WHITE)
        }
    }
    internal class ItemHolder : RecyclerView.ViewHolder {
        var itemLayout: View? = null
        var songItem: TextView
        var ectImageView: ImageView

        constructor(itemView: View?) : super(itemView) {
            songItem = itemView!!.songTextView
            ectImageView = itemView!!.findViewById(R.id.ectImageView)
            this.itemLayout = itemView
            songItem.isSelected = true  //get focus for Marquee
        }
    }
}