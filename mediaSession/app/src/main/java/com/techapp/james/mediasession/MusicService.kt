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
import android.util.Log


class MusicService : MediaBrowserServiceCompat() {
    val MEDIA_ID_ROOT = "__ROOT__";
    override fun onLoadChildren(parentId: String, result: Result<MutableList<MediaBrowserCompat.MediaItem>>) {
        Log.d("MusicService ", "onLoadChildren-----------")
        //Detach(remove) this message from the current thread and allow the sendResult(T) call to happen later.
        result.detach()
// simulation get data process
        var metaData = MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, "" + R.raw.hero_down)
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, "Hero Down")
                .build()
        var mediaItems = ArrayList<MediaBrowserCompat.MediaItem>()
        var mediaItem = MediaBrowserCompat.MediaItem(
                metaData.getDescription(),
                MediaBrowserCompat.MediaItem.FLAG_PLAYABLE)
        mediaItems.add(mediaItem)

    }

    override fun onGetRoot(clientPackageName: String, clientUid: Int, rootHints: Bundle?): BrowserRoot? {
        Log.d("MusicService ", "onGetRoot----------")
        return BrowserRoot(MEDIA_ID_ROOT, null)
    }

    lateinit var mediaPlayer: MediaPlayer
    lateinit var mSession: MediaSessionCompat
    lateinit var mPlaybackState: PlaybackStateCompat
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("MusicService", "onCreate")
        mPlaybackState = PlaybackStateCompat.Builder()
                .setState(PlaybackStateCompat.STATE_NONE, 0, 1.0f)
                .build()
        mSession = MediaSessionCompat(this, MusicService::class.java.simpleName)
        mSession.setFlags(MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        //Sets this flag on the session to indicate that it handles transport control commands through its MediaSessionCompat.Callback.

        // Set the session's token so that client activities can communicate with it. Use MediaSession to connect activity and service.
        mSession.setCallback(sessionCallback)
        //set token will trigger MediaBrowserCompat.ConnectionCallback's callback
        //It mean MediaBrowser and MediaBrowserService connect success
        setSessionToken(mSession.getSessionToken());

        mSession.setPlaybackState(mPlaybackState);
mSession.isActive=true

        mediaPlayer = MediaPlayer()
        mediaPlayer.setOnPreparedListener(object : MediaPlayer.OnPreparedListener {
            override fun onPrepared(mp: MediaPlayer?) {
                mediaPlayer.start();
                mPlaybackState = PlaybackStateCompat.Builder()
                        .setState(PlaybackStateCompat.STATE_PLAYING, 0, 1.0f)
                        .build();
                mSession.setPlaybackState(mPlaybackState);
            }
        })
        mediaPlayer.setOnCompletionListener(object : MediaPlayer.OnCompletionListener {
            override fun onCompletion(mp: MediaPlayer?) {
                mPlaybackState = PlaybackStateCompat.Builder()
                        .setState(PlaybackStateCompat.STATE_NONE, 0, 1.0f)
                        .build();
                mSession.setPlaybackState(mPlaybackState);
                mp!!.reset();
            }
        })
    }

    private val sessionCallback = object : MediaSessionCompat.Callback() {
        override fun onPlay() {
            super.onPlay()
            Log.d("MusicService ", "play")
            if (mPlaybackState.state == PlaybackStateCompat.STATE_PAUSED) {
                mediaPlayer.start()
                mPlaybackState = PlaybackStateCompat.Builder()
                        .setState(PlaybackStateCompat.STATE_PLAYING, 0, 1.0f)
                        .build()
                mSession.setPlaybackState(mPlaybackState)
            }
        }

        override fun onPause() {
            super.onPause()
            Log.d("MusicService ", "pause")
            if (mPlaybackState.state == PlaybackStateCompat.STATE_PLAYING) {
                mediaPlayer.pause()
                mPlaybackState = PlaybackStateCompat.Builder()
                        .setState(PlaybackStateCompat.STATE_PAUSED, 0, 1.0f)
                        .build()
                mSession.setPlaybackState(mPlaybackState)
            }
        }

        override fun onPlayFromUri(uri: Uri?, extras: Bundle?) {
            super.onPlayFromUri(uri, extras)
            Log.d("MusicService ", "onPlayFromUri")
            when (mPlaybackState.state) {
                PlaybackStateCompat.STATE_PLAYING -> {

                }
                PlaybackStateCompat.STATE_PAUSED -> {

                }

                PlaybackStateCompat.STATE_NONE -> {
                    Log.d("MusicService ", "Reset player")
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(this@MusicService, uri)
                    mediaPlayer.prepare()
                    mPlaybackState = PlaybackStateCompat.Builder()
                            .setState(PlaybackStateCompat.STATE_CONNECTING, 0, 1.0f)
                            .build()
                    //save current music info, use to update client UI
                    mSession.setMetadata(MediaMetadataCompat.Builder()
                            .putString(MediaMetadataCompat.METADATA_KEY_TITLE,
                                    extras?.getString("title"))
                            .build())
                }
            }
        }

        override fun onPlayFromSearch(query: String?, extras: Bundle?) {
            super.onPlayFromSearch(query, extras)
        }
    }
}
