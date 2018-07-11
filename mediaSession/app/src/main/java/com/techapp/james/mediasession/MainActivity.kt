package com.techapp.james.mediasession

import android.content.ComponentName
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var songList = ArrayList<MediaBrowserCompat.MediaItem>()
    lateinit var adapter: MusicListAdapter
    lateinit var mBrowser: MediaBrowserCompat
    lateinit var mController: MediaControllerCompat
    val browserConnectionCallback = object : MediaBrowserCompat.ConnectionCallback() {

        override fun onConnectionSuspended() {
            Toast.makeText(this@MainActivity, "MainActivity onConnectionSuspended", Toast.LENGTH_LONG).show()
            super.onConnectionSuspended()
        }

        override fun onConnected() {
            Log.d("MainActivity ", "mBrowser connect success")
            Toast.makeText(this@MainActivity, "MainActivity onConnect", Toast.LENGTH_LONG).show()
            super.onConnected()
            //確保 連接成功 才能進行 訂閱
            mBrowser.takeIf { it.isConnected }.let {
                Log.d("MainActivity ", "mBrowser connect success")
                //Service connection allow client connect, return not null
                var mediaID: String = mBrowser.root
                //browser get data by subscribe
                //get root id
                //must cancel other subscriber (only one can subscribe)
                mBrowser.unsubscribe(mediaID)
                mBrowser.subscribe(mediaID, browserSubscriptionCallback)
                mController = MediaControllerCompat(this@MainActivity, mBrowser.sessionToken)
                mController.registerCallback(object : MediaControllerCompat.Callback() {
                    override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
                        when (state!!.state) {
                            PlaybackStateCompat.STATE_NONE -> {
                                songNameTextView.text = ""
                                playBtn.text = "Start"
                            }

                            PlaybackStateCompat.STATE_PAUSED -> {
                                songNameTextView.text = ""
                                playBtn.text = "Start"
                            }

                            PlaybackStateCompat.STATE_PLAYING -> {
                                songNameTextView.text = "pause"
                                playBtn.text = "Start"
                            }
                        }
                    }

                    override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
                        songNameTextView.text = metadata!!.description.title
                    }
                })
            }
        }

        override fun onConnectionFailed() {
            Toast.makeText(this@MainActivity, "MainActivity onConnect Failed", Toast.LENGTH_LONG).show()
            Log.d("MainActivity ", "Connect fail")
            super.onConnectionFailed()
        }
    }

    private fun rawToUri(id: Int): Uri {
        var uriStr = "android.resource://${packageName}/${id}"
        return Uri.parse(uriStr)
    }

    var browserSubscriptionCallback = object : MediaBrowserCompat.SubscriptionCallback() {
        override fun onChildrenLoaded(parentId: String, children: MutableList<MediaBrowserCompat.MediaItem>, options: Bundle) {
            super.onChildrenLoaded(parentId, children, options)
            Log.d("MainActivity ", "Load MediaItem")
            for (item: MediaBrowserCompat.MediaItem in children) {
                songList.add(item)
            }
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        musicList.layoutManager = LinearLayoutManager(this)
        adapter = MusicListAdapter(this, songList)
        musicList.adapter = adapter
        adapter.setOnItemClickListener(object : MusicListAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                var bundle = Bundle();
                bundle.putString("title", songList[position].getDescription().getTitle().toString());
                mController.getTransportControls().playFromUri(
                        rawToUri(Integer.valueOf(songList.get(position).getMediaId())),
                        bundle
                );
            }

        })
        Log.d("hello", " pass")
        mBrowser = MediaBrowserCompat(
                this,
                ComponentName(this, MusicService::class.java),
                browserConnectionCallback,
                null)
        mBrowser.connect()
        playBtn.setOnClickListener {
            when (mController.getPlaybackState().getState()) {
                PlaybackStateCompat.STATE_PLAYING -> {
                    mController.getTransportControls().pause();
                }
                PlaybackStateCompat.STATE_PAUSED -> {
                    mController.getTransportControls().play();
                }
                else -> {
                    mController.getTransportControls().playFromSearch("", null);
                }
            }

        }
    }

    fun testPlayer() {
        var mediaPlayer = MediaPlayer()
        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.hero_down)
        mediaPlayer.start()
        mediaPlayer.setOnPreparedListener(object : MediaPlayer.OnPreparedListener {
            override fun onPrepared(mp: MediaPlayer?) {
                mediaPlayer.start();
            }
        })
        mediaPlayer.setOnCompletionListener(object : MediaPlayer.OnCompletionListener {
            override fun onCompletion(mp: MediaPlayer?) {
                mp!!.reset();
            }
        })

        mediaPlayer.start()
    }

    override fun onStart() {
        super.onStart()
//        mBrowser.connect()
        Log.d("no ", "pass")
    }

    override fun onStop() {
        super.onStop()
        mBrowser.disconnect()
    }
}
