package com.techapp.james.musicdemo.view.fragmentPage.albumFragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techapp.james.musicdemo.R
import com.techapp.james.musicdemo.model.compressPicture.CompressPicture
import com.techapp.james.musicdemo.model.musicModel.ManageCurrentPlaySongList
import com.techapp.james.musicdemo.model.musicModel.PlayList
import com.techapp.james.musicdemo.model.playManager.ExoPlayManagerSubject
import com.techapp.james.musicdemo.model.playManager.ExoPlayerManager
import com.techapp.james.musicdemo.model.threadModel.ThreadConfig
import com.techapp.james.musicdemo.view.fragmentPage.fragmentRestart.ReStart
import kotlinx.android.synthetic.main.album_fragment_album_layout.*


class AlbumFragment : Fragment(), ReStart, AppBarLayout.OnOffsetChangedListener, ExoPlayManagerSubject {
    private var playListHandler: Handler? = null
    override fun updateExoPlayerState() {
        if (playBtn != null) {  //Maybe ui is not here,now. So playBtn will be null.
            if (playList!!.equals(ManageCurrentPlaySongList.playList)) {
                if (exoPlayerManager.isPlay()) {
                    playBtn.setText("PLAY")
                    var drawable = ContextCompat.getDrawable(context, R.drawable.ic_play_arrow_white)
                    playBtn.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
                    //setCompoundDrawablesWithIntrinsicBounds this method can let's drawable show on button left top right bottom
                } else {
                    playBtn.setText("PAUSE")
                    var drawable = ContextCompat.getDrawable(context, R.drawable.ic_pause_white_24dp)
                    playBtn.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
                    //setCompoundDrawablesWithIntrinsicBounds this method can let's drawable show on button left top right bottom
                }
            }
        }
    }

    var exoPlayerManager = ExoPlayerManager
    var playList: PlayList? = null
    override fun onRestart() {  //This restart is activity's restart.
    }

    companion object {
        private val instance = AlbumFragment()
        public fun getInstance(playList: PlayList): Fragment {
            instance.playList = playList
            return instance
        }
    }

    override fun onStop() { // reset flag or it will cause wrong in pressing PLAY button
        super.onStop()
        flag = false
    }

    var flag = false
    var albumAdapter: albumListAdapter? = null
    override fun onStart() {
        super.onStart()
        var compressPicture = CompressPicture()
        var bitmap = compressPicture.compressBySize(context.resources, R.drawable.party_picture, 200, 100)
        albumPicture.setImageBitmap(bitmap)
        albumNameTextView.text = playList!!.name
        playListName.text = playList!!.name
        albumRecyclerList.layoutManager = LinearLayoutManager(this.context)
        albumAdapter = albumListAdapter(this.context, this.playList!!)
        albumRecyclerList.adapter = albumAdapter
        appbar.addOnOffsetChangedListener(this)
        exoPlayerManager.registSubject(this)
        if (judgePlayListCurrentPlay()) {
            var drawable = ContextCompat.getDrawable(context, R.drawable.ic_pause_white_24dp)
            playBtn.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
            playBtn.setText("PAUSE")
            flag = true
        }
        playBtn.setOnClickListener {
            if (this.playList!!.list.size > 0) {
                if (judgeIsFirstPressPlayBtn()) {
                    Log.d("AlbumFragment ", " Pass")
                    ManageCurrentPlaySongList.playList = this.playList!!
                    ManageCurrentPlaySongList.setCurrentIndex(0)
                    exoPlayerManager.startPlay(ManageCurrentPlaySongList.getCurrentSong().source, true)
                    albumAdapter!!.setColorInListItem()
                    playBtn.setText("PAUSE")
                    var drawable = ContextCompat.getDrawable(context, R.drawable.ic_pause_white_24dp)
                    playBtn.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
                    flag = true
                } else {
                    if (flag) {
                        playBtn.setText("PLAY")
                        exoPlayerManager.stopPlay()
                        ManageCurrentPlaySongList.notifyCurrentSongNameAndPlayBtn()
                        var drawable = ContextCompat.getDrawable(context, R.drawable.ic_play_arrow_white)
                        playBtn.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
                        flag = false
                    } else if (!flag) {
                        exoPlayerManager.play()
                        ManageCurrentPlaySongList.notifyCurrentSongNameAndPlayBtn()
                        playBtn.setText("PAUSE")
                        var drawable = ContextCompat.getDrawable(context, R.drawable.ic_pause_white_24dp)
                        playBtn.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
                        flag = true
                    }
                }
            }
        }
        leftImageView.setOnClickListener {
            getActivity().onBackPressed()
        }
        playListHandler = object : Handler() {
            override fun handleMessage(msg: Message?) {
                if (albumRecyclerList != null) {
                    if (msg!!.what == ThreadConfig.PLAYLIST_UPDATE_MSG) {
                        albumAdapter!!.setColorInListItem()
                    }
                }
            }
        }
        exoPlayerManager.setAlbumPlayListHandler(playListHandler!!)
    }

    fun judgeIsFirstPressPlayBtn(): Boolean {
        return (playList!!.list.size > 0 && !flag && (!playList!!.equals(ManageCurrentPlaySongList.playList)))
    }

    fun judgePlayListCurrentPlay(): Boolean {
        return (playList!!.list.size > 0 && (playList!!.equals(ManageCurrentPlaySongList.playList)))
    }

    //    list.remove(position);
//    recycler.removeViewAt(position);
//    mAdapter.notifyItemRemoved(position);
//    mAdapter.notifyItemRangeChanged(position, list.size());
//
//        2) UPDATE THE DATA: The only things I had to do is
//
//        mAdapter.notifyDataSetChanged();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.album_fragment_album_layout, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()

    }

    override fun onDestroy() {
        super.onDestroy()
        albumAdapter = null
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        var totalRange = Math.abs(appbar.totalScrollRange)
        var verticalOffset = Math.abs(verticalOffset)
        maskTextView.background.alpha = caculateMaskAlpha(totalRange, verticalOffset)
    }

    fun caculateMaskAlpha(totalRange: Int, verticalOffset: Int): Int {
        //Alpha range is 0~255 0 mean transparence
        var scrollRate: Float = verticalOffset.toFloat() / totalRange.toFloat()
        var alpha = 255 - 255 * scrollRate
        return alpha.toInt()
    }
}// Required empty public constructor
