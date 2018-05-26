package com.techapp.james.musicdemo.model.playManager

import android.net.Uri
import android.os.Handler
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.LoopingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.techapp.james.musicdemo.model.threadModel.LooperThreadProgress

/**
 * Created by James on 2018/3/31.
 */
object ExoPlayerManager {
    private var UIProgressHandler: Handler? = null
    private var musicThread: LooperThreadProgress?
    private var player: SimpleExoPlayer? = null
    private var configure = Configure
    private var uri: String = ""
    private var subjects: ArrayList<ExoPlayManagerSubject>

    init {
        musicThread = LooperThreadProgress()
        subjects = ArrayList<ExoPlayManagerSubject>()
    }

    fun isPlay(): Boolean {
        if (player == null) {
            return false
        }
        return player!!.playWhenReady
    }

    fun play() {
        notification()
        if (player == null) {
            return
        }
        player!!.playWhenReady = true
    }

    fun preparePlayExo(): MediaSource {
        //  println("hello exo play")
        var bandwidthMeter = DefaultBandwidthMeter()
        var videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        var trackSelector = DefaultTrackSelector(videoTrackSelectionFactory) //A track selector is responsible for
        //// selecting from the list of tracks (audio, video or text) passed to ExoPlayer via media source.
        //Create the play
        player = ExoPlayerFactory.newSimpleInstance(configure.context, trackSelector)
        // Measures bandwidth during playback. Can be null if not required.
        val bandwidthMeter_Factory = DefaultBandwidthMeter()
        // Produces DataSource instances through which media data is loaded.
        val dataSourceFactory = DefaultDataSourceFactory(configure.context,
                Util.getUserAgent(configure.context, configure.applicationName), bandwidthMeter_Factory)
        val videoSource = ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(uri))
        val loopingMediaSource = LoopingMediaSource(videoSource)
        return loopingMediaSource

    }

    fun releasePlay() {
        if (player != null) {
            player!!.playWhenReady = false
            player!!.release()
            player = null
        }
    }

    fun stopPlay() {
        notification()
        if (player == null) {
            return
        }
        player!!.playWhenReady = false
    }

    fun seekTo(progress: Int) {
        if (player == null) {
            return
        }
        var temp = (progress * 1000).toLong()
        player!!.seekTo(temp)
    }

    public fun setUIHandler(handler: Handler) {
        UIProgressHandler = handler
        if (musicThread != null) {
            musicThread!!.setProgressHandle(UIProgressHandler!!)
        }
    }

    fun setMainHandler(handler: Handler) {
        musicThread!!.setMainHandle(handler)
    }

    fun setAlbumPlayListHandler(handler: Handler) {
        musicThread!!.setUpdateHandle(handler)
    }

    public fun startPlay(uri: String, isPlay: Boolean) {
        notification()
        this.uri = uri
        if (player != null) {
            player!!.release()
        }

        var videoSource = preparePlayExo()
        if (player != null) {
            player!!.prepare(videoSource)
            musicThread!!.setPlayer(player)
            musicThread!!.start()
            if (isPlay) {
                play()
            }
        }
    }

    public fun registSubject(subject: ExoPlayManagerSubject) {
        subjects.add(subject)
    }

    private fun notification() {
        for (i in 0..(subjects.size - 1)) {
            subjects[i].updateExoPlayerState()
        }
    }
}