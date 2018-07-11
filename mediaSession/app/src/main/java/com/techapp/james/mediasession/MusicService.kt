package com.techapp.james.mediasession

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserServiceCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.text.TextUtils
import android.util.Log
import java.io.IOException


class MusicService : MediaBrowserServiceCompat() {

    private var mSession: MediaSessionCompat? = null
    private var mMediaPlayer: MediaPlayer? = null
    private var mPlaybackState: PlaybackStateCompat? = null

    /**
     * communicate controller call back
     */
    private var SessionCallback: android.support.v4.media.session.MediaSessionCompat.Callback? = null

    /**
     * 监听MediaPlayer.prepare()
     */
    private val PreparedListener = MediaPlayer.OnPreparedListener {
        mMediaPlayer!!.start()
        mPlaybackState = PlaybackStateCompat.Builder()
                .setState(PlaybackStateCompat.STATE_PLAYING, 0, 1.0f)
                .build()
        mSession!!.setPlaybackState(mPlaybackState)
    }

    /**
     * listen end event
     */
    private val CompletionListener = MediaPlayer.OnCompletionListener {
        mPlaybackState = PlaybackStateCompat.Builder()
                .setState(PlaybackStateCompat.STATE_NONE, 0, 1.0f)
                .build()
        mSession!!.setPlaybackState(mPlaybackState)
        mMediaPlayer!!.reset()
    }

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG, "onCreate-----------")

        mPlaybackState = PlaybackStateCompat.Builder()
                .setState(PlaybackStateCompat.STATE_NONE, 0, 1.0f)
                .build()

        mSession = MediaSessionCompat(this, "MusicService")
        mSession!!.setCallback(SessionCallback)
        mSession!!.setFlags(MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
        mSession!!.setPlaybackState(mPlaybackState)

        //After set token will trigger MediaBrowserCompat.ConnectionCallback callback
        //It mean MediaBrowser and MediaBrowserService connect success
        sessionToken = mSession!!.sessionToken

        mMediaPlayer = MediaPlayer()
        mMediaPlayer!!.setOnPreparedListener(PreparedListener)
        mMediaPlayer!!.setOnCompletionListener(CompletionListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mMediaPlayer != null) {
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
        if (mSession != null) {
            mSession!!.release()
            mSession = null
        }
    }

    override fun onGetRoot(clientPackageName: String, clientUid: Int, rootHints: Bundle?): MediaBrowserServiceCompat.BrowserRoot? {
        Log.e(TAG, "onGetRoot-----------")
        return MediaBrowserServiceCompat.BrowserRoot(MEDIA_ID_ROOT, null)
    }

    override fun onLoadChildren(parentId: String, result: MediaBrowserServiceCompat.Result<List<MediaBrowserCompat.MediaItem>>) {
        Log.e(TAG, "onLoadChildren--------")
        //Detach this message from the current thread and allow the sendResult(T) call to happen later.
        result.detach()
        // Here is where we get media.Maybe in network ...etc
        val metadata = MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, "" + R.raw.hero_down)
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, "Hero down")
                .build()
        val mediaItems = ArrayList<MediaBrowserCompat.MediaItem>()
        mediaItems.add(createMediaItem(metadata))

        //Send data to Browser ,It will trigger onChildLoad in Subscribe callback in MainActivity
        result.sendResult(mediaItems)
    }

    private fun createMediaItem(metadata: MediaMetadataCompat): MediaBrowserCompat.MediaItem {
        return MediaBrowserCompat.MediaItem(
                metadata.description,
                MediaBrowserCompat.MediaItem.FLAG_PLAYABLE
        )
    }

    init {
        SessionCallback = object : MediaSessionCompat.Callback() {
            /**
             * Here communicate with MediaController.getTransportControls().play
             */
            override fun onPlay() {
                Log.e(TAG, "onPlay")
                if (mPlaybackState!!.state == PlaybackStateCompat.STATE_PAUSED) {
                    mMediaPlayer!!.start()
                    mPlaybackState = PlaybackStateCompat.Builder()
                            .setState(PlaybackStateCompat.STATE_PLAYING, 0, 1.0f)
                            .build()
                    mSession!!.setPlaybackState(mPlaybackState)
                }
            }

            /**
             * Here communicate with MediaController.getTransportControls().onPause
             */
            override fun onPause() {
                Log.e(TAG, "onPause")
                if (mPlaybackState!!.state == PlaybackStateCompat.STATE_PLAYING) {
                    mMediaPlayer!!.pause()
                    mPlaybackState = PlaybackStateCompat.Builder()
                            .setState(PlaybackStateCompat.STATE_PAUSED, 0, 1.0f)
                            .build()
                    mSession!!.setPlaybackState(mPlaybackState)
                }
            }

            /**
             * Here communicate with MediaController.getTransportControls().playFromUri
             *
             * @param uri
             * @param extras
             */
            override fun onPlayFromUri(uri: Uri?, extras: Bundle?) {
                Log.e(TAG, "onPlayFromUri")
                try {
                    when (mPlaybackState!!.state) {
                        PlaybackStateCompat.STATE_PLAYING, PlaybackStateCompat.STATE_PAUSED, PlaybackStateCompat.STATE_NONE -> {
                            mMediaPlayer!!.reset()
                            mMediaPlayer!!.setDataSource(this@MusicService, uri!!)
                            mMediaPlayer!!.prepare()//mediaPlayer prepare synchornize
                            mPlaybackState = PlaybackStateCompat.Builder()
                                    .setState(PlaybackStateCompat.STATE_CONNECTING, 0, 1.0f)
                                    .build()
                            mSession!!.setPlaybackState(mPlaybackState)

                            //save media item , use to update UI

                            mSession!!.setMetadata(MediaMetadataCompat.Builder()
                                    .putString(MediaMetadataCompat.METADATA_KEY_TITLE, extras!!.getString("title"))
                                    .build()
                            )
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

            override fun onPlayFromSearch(query: String?, extras: Bundle?) {}
        }
    }

    companion object {

        private val TAG = "MusicService"
        val MEDIA_ID_ROOT = "__ROOT__"
    }
}
