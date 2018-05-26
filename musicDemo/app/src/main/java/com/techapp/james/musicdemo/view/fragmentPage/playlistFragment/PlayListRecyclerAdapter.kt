package com.techapp.james.musicdemo.view.fragmentPage.playlistFragment

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import com.techapp.james.musicdemo.R
import com.techapp.james.musicdemo.model.musicModel.PlayList
import com.techapp.james.musicdemo.model.musicModel.PlayLists
import kotlinx.android.synthetic.main.playlist_fragment_playlist_item.view.*

/**
 * Created by James on 2018/3/31.
 */
class PlayListRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private var context: Context
    private var data: PlayLists
    private var changePage: ChangePage? = object : ChangePage {
        override fun intentToAlbumPage(songList: PlayList) {
        }
    }

    constructor(context: Context, data: PlayLists) {
        this.context = context
        this.data = data
    }

    interface ChangePage {
        fun intentToAlbumPage(songList: PlayList)
    }

    fun setChangePage(changePage: ChangePage) {
        this.changePage = changePage
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.playlist_fragment_playlist_item, parent, false)
        return PlayListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as PlayListItemViewHolder).songNameTextView.text = data.get(position).name
        (holder as PlayListItemViewHolder).itemView.setOnClickListener {
            this.changePage!!.intentToAlbumPage(data[position])
        }
        (holder as PlayListItemViewHolder).ectImageView.setOnClickListener {
            var popup = PopupMenu(context, holder.ectImageView)
            popup.menuInflater.inflate(R.menu.playlist_fragment_recycler_menu, popup.menu)
            popup.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.removePlayList -> {
                        var songList=data[position]
                        data.remove(songList)
                        this.notifyDataSetChanged()
                    }
                }
                true
            }
            popup.show()
        }
    }

    internal class PlayListItemViewHolder : RecyclerView.ViewHolder {
        var songNameTextView: TextView
        var ectImageView: ImageView

        constructor(itemView: View?) : super(itemView) {
            songNameTextView = itemView!!.songListNameTextView
            ectImageView = itemView!!.ectImageView
        }
    }
}