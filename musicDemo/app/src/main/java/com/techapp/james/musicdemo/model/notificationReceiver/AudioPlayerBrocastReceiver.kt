package com.techapp.james.musicdemo.model.notificationReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.techapp.james.musicdemo.model.musicModel.ManageCurrentPlaySongList
import com.techapp.james.musicdemo.model.playManager.ExoPlayerManager

/**
 * Created by James on 2018/4/9.
 */
class AudioPlayerBrocastReceiver : BroadcastReceiver {

    constructor()

    override fun onReceive(context: Context?, intent: Intent?) {
        var action = intent!!.action
        when (action) {
            "com.techapp.james.ACTION_NEXT" -> {
                Log.d("AudioPlayerBrocast", "Action " + action)
                var next = ManageCurrentPlaySongList.getCurrentIndex()!! + 1
                if (next < ManageCurrentPlaySongList.playList.list.size) {
                    ManageCurrentPlaySongList.setCurrentIndex(next)
                    ExoPlayerManager.startPlay(ManageCurrentPlaySongList.getCurrentSong().source, true)
                }
            }
            "com.techapp.james.ACTION_PLAY" -> {
                Log.d("AudioPlayerBrocast", "Action " + action)
                if (ExoPlayerManager.isPlay()) {
                    ExoPlayerManager.stopPlay()
                } else {
                    ExoPlayerManager.play()
                }
            }
            "com.techapp.james.ACTION_PREVIOUS" -> {
                Log.d("AudioPlayerBrocast", "Action " + action)
                var previous = ManageCurrentPlaySongList.getCurrentIndex()!! - 1
                if (previous < ManageCurrentPlaySongList.playList.list.size && previous > -1) {
                    ManageCurrentPlaySongList.setCurrentIndex(previous)
                    ExoPlayerManager.startPlay(ManageCurrentPlaySongList.getCurrentSong().source, true)
                }
            }
        }
    }
}