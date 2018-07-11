package com.techapp.james.mediasession

import android.content.ComponentName
import android.content.Context
import android.media.AudioManager
import android.net.Uri
import android.os.Bundle
import android.os.RemoteException
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.KeyEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var list: MutableList<MediaBrowserCompat.MediaItem>? = null
    private var musicAdapter: MusicListAdapter? = null
    private var layoutManager: LinearLayoutManager? = null

    private var mBrowser: MediaBrowserCompat? = null
    private var mController: MediaControllerCompat? = null

    /**
     * connect to Browser, This is browser callback.
     */
    private val BrowserConnectionCallback = object : MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            Log.e(TAG, "onConnected------")
            if (mBrowser!!.isConnected) {
                //mediaId is MediaBrowserService.onGetRoot return value
                //if Service allow client connect，return value isn't null， valus is root ID
                // refuse connect , value is null
                val mediaId = mBrowser!!.root

                //Browser subscribe Service to get data, it request two parameters, one is mediaId another is calback
                //if mediaId subscribed by other , it need to cancel other subscriber.
                //Although subscriber will be cover, it will not call onChildCall(Callback)
//google say
                // This is temporary: A bug is being fixed that will make subscribe
                // consistently call onChildrenLoaded initially, no matter if it is replacing an existing
                // subscriber or not. Currently this only happens if the mediaID has no previous
                // subscriber or if the media content changes on the service side, so we need to
                // unsubscribe first.
                mBrowser!!.unsubscribe(mediaId)
                //subscribe will trigger onLoadChildren in MusicService
                mBrowser!!.subscribe(mediaId, BrowserSubscriptionCallback)

                try {
                    mController = MediaControllerCompat(applicationContext, mBrowser!!.sessionToken)
                    mController!!.registerCallback(ControllerCallback)
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }

            }
        }

        override fun onConnectionFailed() {
            Log.e(TAG, " connect fail")
        }
    }
    /**
     * send data subscribe callback to MediaBrowserService
     */
    private val BrowserSubscriptionCallback = object : MediaBrowserCompat.SubscriptionCallback() {
        override fun onChildrenLoaded(parentId: String,
                                      children: List<MediaBrowserCompat.MediaItem>) {
            Log.e(TAG, "onChildrenLoaded------")
            //children service send media collection
            for (item in children) {
                Log.e(TAG, item.description.title!!.toString())
                list!!.add(item)
            }
            musicAdapter!!.notifyDataSetChanged()
        }
    }

    /**
     *  change UI callback
     */
    private val ControllerCallback = object : MediaControllerCompat.Callback() {
        /***
         * music change state change callback
         * @param state
         */
        override fun onPlaybackStateChanged(state: PlaybackStateCompat) {
            when (state.state) {
                PlaybackStateCompat.STATE_NONE// none state
                -> {
                    textTitle!!.text = ""
                    btnPlay!!.text = "start"
                }
                PlaybackStateCompat.STATE_PAUSED -> btnPlay!!.text = "start"
                PlaybackStateCompat.STATE_PLAYING -> btnPlay!!.text = "pause"
            }
        }

        /**
         * change music callback
         * @param metadata
         */
        override fun onMetadataChanged(metadata: MediaMetadataCompat) {
            textTitle!!.text = metadata.description.title
        }
    }
    lateinit var mAudioManager: AudioManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAudioManager = this.getSystemService(Context.AUDIO_SERVICE) as AudioManager;
        mBrowser = MediaBrowserCompat(
                this,
                ComponentName(this, MusicService::class.java!!), //bind browser service
                BrowserConnectionCallback, null)// set Connect callback

        list = ArrayList<MediaBrowserCompat.MediaItem>()
        layoutManager = LinearLayoutManager(this)
        musicAdapter = MusicListAdapter(this, list as ArrayList<MediaBrowserCompat.MediaItem>)
        musicAdapter!!.setOnItemClickListener(object : MusicListAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val bundle = Bundle()
                bundle.putString("title", list!![position].description.title!!.toString())
                mController!!.transportControls.playFromUri(
                        rawToUri(Integer.valueOf(list!![position].mediaId)),
                        bundle
                )
            }
        })
        btnPlay.setOnClickListener {
            mController?.let {
                when (mController!!.playbackState.state) {
                    PlaybackStateCompat.STATE_PLAYING -> mController!!.transportControls.pause()
                    PlaybackStateCompat.STATE_PAUSED -> mController!!.transportControls.play()
                    else -> mController!!.transportControls.playFromSearch("", null)
                }
            }
        }
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = musicAdapter
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            //Do something
            //降低音量，调出系统音量控制
            mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER,
                    AudioManager.FX_FOCUS_NAVIGATION_UP);
            return true
        }
//增加音量，调出系统音量控制
        else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE,
                    AudioManager.FX_FOCUS_NAVIGATION_UP);
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onStart() {
        super.onStart()
        //Browser send connection to service
        mBrowser!!.connect()
    }

    override fun onStop() {
        super.onStop()
        mBrowser!!.disconnect()
    }

    private fun rawToUri(id: Int): Uri {
        val uriStr = "android.resource://$packageName/$id"
        return Uri.parse(uriStr)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    companion object {
        private val TAG = "MainActivity"
    }
}
