package com.techapp.james.musicdemo.view.choosePlayListView

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.techapp.james.musicdemo.R
import com.techapp.james.musicdemo.model.musicModel.PlayLists
import com.techapp.james.musicdemo.view.fragmentPage.firstFragment.PassConfigure
import kotlinx.android.synthetic.main.choose_playlist_view_playlist_item.view.*

/**
 * Created by James on 2018/4/2.
 */
class ChoosePlayListRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    var data: PlayLists
    var context: Context

    constructor(context: Context, data: PlayLists) {
        this.context = context
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.choose_playlist_view_playlist_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        var itemHolder = (holder as ItemViewHolder)
        itemHolder.songListName.text = data[position].name.toString()
        itemHolder.itemLayout!!.setOnClickListener {
            data[position].list.add(PassConfigure.passSong!!)
            (context as Activity).finish()
        }
    }

    internal class ItemViewHolder : RecyclerView.ViewHolder {
        val songListName: TextView
        val itemLayout: View?

        constructor(itemView: View?) : super(itemView) {
            songListName = itemView!!.songListNameTextView
            this.itemLayout = itemView
        }
    }
}