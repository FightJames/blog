package com.techapp.james.musicdemo.view.fragmentPage.firstFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techapp.james.musicdemo.R
import com.techapp.james.musicdemo.model.musicModel.phoneMusicData.OriginMusicData
import com.techapp.james.musicdemo.view.fragmentPage.OnFragmentInteration
import com.techapp.james.musicdemo.view.fragmentPage.fragmentRestart.ReStart
import kotlinx.android.synthetic.main.fragment_first.*


class FirstFragment : Fragment(), ReStart {
    var firstFragmentListAdapter: SongRecyclerAdapter? = null

    companion object {
        private val instance = FirstFragment()
        public fun getInstance(): Fragment {
            return instance
        }
    }

    public var recyclerAdapter: SongRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_first, container, false)
    }

    override fun onStart() {
        super.onStart()
        println("onStart")
        songListRecycler.layoutManager = LinearLayoutManager(this.context)
        try {
            firstFragmentListAdapter = SongRecyclerAdapter(OriginMusicData.originPlayList!!, this.context, this.fragmentManager)
            songListRecycler.adapter = firstFragmentListAdapter
        } catch (e: Exception) {

        }

        this.activity.let {
            (it as OnFragmentInteration).onFragmentInteration(it.getString(R.string.fragmentSecond))
        }
    }

    fun randomSongDisplay() {
        if (songListRecycler != null) {
            firstFragmentListAdapter!!.setColorInListItem()
        }
    }

    fun onPlayListCurrentSongChangeColorEvent() {
        if (songListRecycler != null) {
            firstFragmentListAdapter!!.setColorInListItem()
        }
    }

    override fun onRestart() {
        if (songListRecycler != null) {
            firstFragmentListAdapter!!.setColorInListItem()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        firstFragmentListAdapter = null
    }
}