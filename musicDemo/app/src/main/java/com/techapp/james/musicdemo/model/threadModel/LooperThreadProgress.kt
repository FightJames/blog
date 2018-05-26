package com.techapp.james.musicdemo.model.threadModel

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import com.google.android.exoplayer2.Player
import com.techapp.james.musicdemo.model.musicModel.ManageCurrentPlaySongList
import com.techapp.james.musicdemo.model.musicModel.SongConfigure
import com.techapp.james.musicdemo.model.playManager.Configure
import com.techapp.james.musicdemo.model.playManager.ExoPlayerManager
import com.techapp.james.musicdemo.view.musicView.MusicPlayConfigure
import com.techapp.james.musicdemo.view.musicView.RepeatMode

/**
 * Created by James on 2018/3/17.
 */
class LooperThreadProgress : Thread { //It will communicate with MainActivity and Album fragment page
    private var uiHandler: Handler = NullHandler  //communicate with main ui
    private var mainHandler: Handler = NullHandler // post task to main thread
    private var updatePlayListHandler: Handler = NullHandler  //this handler update album ui PlayList
    private var play: Player? = null
    private var flag = true
    private var nowTime: Long = 0L
    private var lastTime: Long = 0L
    private var delayTime = 500  //prevent double call recycle funtion

    constructor() {
        //     println("Thread " + this.id)
    }

    fun setPlayer(play: Player?) {
        this.play = play
    }

    fun setProgressHandle(handler: Handler) {
        uiHandler = handler
    }

    fun setMainHandle(handler: Handler) {
        mainHandler = handler
    }

    fun setUpdateHandle(handler: Handler) {
        updatePlayListHandler = handler

    }

    override fun run() {
        var bundle: Bundle = Bundle()
        while (flag) {
            if (play != null) {
                Thread.sleep(200)
                var message = Message()
                bundle.clear()
                var currentPosition = (play!!.currentPosition / 1000) //second
                var maxTime = (play!!.duration / 1000) //all time second
                bundle.putInt(Configure.currentPosition, currentPosition.toInt())
                bundle.putInt(Configure.maxTime, maxTime.toInt())
                message.data = bundle
                message.what = ThreadConfig.PROGRESSBAR_MSG
                uiHandler.sendMessage(message)

                if (isEnding(currentPosition, maxTime)) {
//here will call muti time, it will cause recycle play skip muti songs.
                    when (MusicPlayConfigure.mode) {
                        RepeatMode.NoRepeat -> {
                            if (!MusicPlayConfigure.isRandom) {
                                mainHandler.post(object : Runnable {
                                    override fun run() {
                                        ExoPlayerManager.stopPlay()
                                        ExoPlayerManager.seekTo(0)

                                    }
                                })
                                var message = Message()
                                message.what = ThreadConfig.PLAY_BTN_MSG
                                uiHandler.sendMessage(message)
                            }
                        }
                        RepeatMode.RepeatAllAlbum -> {
                            nowTime = System.currentTimeMillis()
                            if ((nowTime - lastTime) >= delayTime) {
                                var next = (ManageCurrentPlaySongList.getCurrentIndex()!! + 1)
                                if (next < ManageCurrentPlaySongList.playList.list.size) {
                                    mainHandler.post(object : Runnable {
                                        override fun run() {
                                            ManageCurrentPlaySongList.setCurrentIndex(next)
                                        }
                                    })
                                    var msg = Message()
                                    msg.what = ThreadConfig.PLAYLIST_UPDATE_MSG
                                    updatePlayListHandler.sendMessage(msg)
                                    msg = Message()
                                    msg.what = ThreadConfig.PLAYLIST_UPDATE_MSG
                                    uiHandler.sendMessage(msg)

                                } else {
                                    mainHandler.post(object : Runnable {
                                        override fun run() {
                                            ManageCurrentPlaySongList.setCurrentIndex(0)
                                        }
                                    })
                                    var msg = Message()
                                    msg.what = ThreadConfig.PLAYLIST_UPDATE_MSG
                                    updatePlayListHandler.sendMessage(msg)
                                    msg = Message()
                                    msg.what = ThreadConfig.PLAYLIST_UPDATE_MSG
                                    uiHandler.sendMessage(msg)
                                }
                                mainHandler.post(object : Runnable {
                                    override fun run() {
                                        try {
                                            var song = ManageCurrentPlaySongList.getCurrentSong()
                                            ExoPlayerManager.startPlay(song.source, true)
                                        } catch (e: Exception) {
                                            ExoPlayerManager.stopPlay()
                                        }
                                    }
                                })
                            }
                            lastTime = nowTime
                        }
                        RepeatMode.RepeatSingle -> {

                        }
                    }

                    if (executeRandomSong()) {
                        nowTime = System.currentTimeMillis()
                        if ((nowTime - lastTime) >= delayTime) {
                            mainHandler.post(object : Runnable {
                                override fun run() {
                                    try {
                                        ExoPlayerManager.startPlay(ManageCurrentPlaySongList.getRandomSong().source, true)
                                    } catch (e: Exception) {
                                        ExoPlayerManager.stopPlay()
                                    }
                                }
                            })
                            var message = Message()
                            message.what = ThreadConfig.RANDOM_MSG
                            uiHandler.sendMessage(message)
                            lastTime = nowTime
                        }
                    }
                }
            }
        }

    }


    private fun executeRandomSong(): Boolean { // if loop mode is true, it will not execute random song.
        return MusicPlayConfigure.isRandom && (MusicPlayConfigure.mode == RepeatMode.NoRepeat) && ExoPlayerManager.isPlay()
    }

    private fun isEnding(currentTime: Long, allTime: Long): Boolean { //Judge song is ended.
        return currentTime == allTime && ExoPlayerManager.isPlay()
    }

    private fun stopThread() {
        flag = false
    }
//call this method and then point null in outside
}