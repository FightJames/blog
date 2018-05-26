package com.techapp.james.musicdemo.view.fragmentPage.playlistFragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.techapp.james.musicdemo.R
import com.techapp.james.musicdemo.model.musicModel.PlayList
import com.techapp.james.musicdemo.model.musicModel.PlayLists
import com.techapp.james.musicdemo.view.createPlaylistView.CreatePlayListActivity
import com.techapp.james.musicdemo.view.fragmentPage.albumFragment.AlbumFragment
import com.techapp.james.musicdemo.view.fragmentPage.fragmentRestart.ReStart
import com.techapp.james.musicdemo.view.main.MainActivity
import kotlinx.android.synthetic.main.playlist_fragment_fragment_play_list.*


class PlayListFragment : Fragment(), ReStart {
    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onRestart() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.playlist_fragment_fragment_play_list, container, false)
    }

    override fun onStart() {
        super.onStart()
        playListRecycler.layoutManager = LinearLayoutManager(this.context)
        var adapter = PlayListRecyclerAdapter(this.context, PlayLists.instance)
        playListRecycler.adapter = adapter
        //(activity as MainActivity).mainPresenter
        adapter.setChangePage(object : PlayListRecyclerAdapter.ChangePage {
            override fun intentToAlbumPage(playList: PlayList) {
                var fragmentTransaction = activity.supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frameLayout, AlbumFragment.getInstance(playList))
                fragmentTransaction.addToBackStack("albumPage");
                fragmentTransaction.commit()
            }
        })
        addImageView.setOnClickListener {
            var intent = Intent()
            intent.setClass(this.context, CreatePlayListActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    companion object {
        private val fragment = PlayListFragment()
        fun getInstance(): PlayListFragment {
            return fragment
        }
    }
}// Required empty public constructor
