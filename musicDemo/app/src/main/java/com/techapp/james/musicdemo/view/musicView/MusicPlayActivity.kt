package com.techapp.james.musicdemo.view.musicView

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.SeekBar
import com.techapp.james.musicdemo.R
import com.techapp.james.musicdemo.model.musicModel.ManageCurrentPlaySongList
import com.techapp.james.musicdemo.model.playManager.Configure
import com.techapp.james.musicdemo.model.playManager.ExoPlayManagerSubject
import com.techapp.james.musicdemo.model.playManager.ExoPlayerManager
import com.techapp.james.musicdemo.model.threadModel.ThreadConfig
import kotlinx.android.synthetic.main.music_view_activity_music_play.*

class MusicPlayActivity : AppCompatActivity(), ExoPlayManagerSubject {
    override fun updateExoPlayerState() {
        if (exoPlayerManager!!.isPlay()) {
            playImageView.visibility = View.VISIBLE
            stopImageView.visibility = View.INVISIBLE
        } else {
            playImageView.visibility = View.INVISIBLE
            stopImageView.visibility = View.VISIBLE

        }
    }

    var exoPlayerManager = ExoPlayerManager
    var musicPlayProgressHandler: Handler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.music_view_activity_music_play)
        init()
    }

    private fun init() {
        ExoPlayerManager.registSubject(this)
        musicPlayProgressHandler = object : Handler() {
            override fun handleMessage(msg: Message?) {
                when (msg!!.what) {
                    ThreadConfig.PROGRESSBAR_MSG -> {
                        var bundle = msg!!.data
                        var currentPosition = bundle.get(Configure.currentPosition)
                        var maxTime = bundle.get(Configure.maxTime)
                        songSeekBar.setMax(maxTime as Int)
                        songSeekBar.setProgress(currentPosition as Int)
                        var min = currentPosition / 60
                        var second = currentPosition % 60
                        currentTimeTextView.text = min.toString() + ":" + dealStringSecond(second.toString())
                        var allMin = maxTime / 60
                        var allSecond = maxTime % 60
                        if (allMin.toString().length > 5 || allSecond.toString().length > 5) {
                            allTimeTextView.text = "0:00"
                        } else {
                            allTimeTextView.text = allMin.toString() + ":" + dealStringSecond(allSecond.toString())
                        }
                    }
                    ThreadConfig.PLAY_BTN_MSG -> {
                        playImageView.visibility = View.VISIBLE
                        stopImageView.visibility = View.INVISIBLE
                    }
                    ThreadConfig.RANDOM_MSG -> {
                        dealSongPicture()
                    }
                }

            }
        }

        exoPlayerManager!!.setUIHandler(musicPlayProgressHandler!!)
        if (exoPlayerManager!!.isPlay()) {
            playImageView.visibility = View.INVISIBLE
            stopImageView.visibility = View.VISIBLE
        } else {
            playImageView.visibility = View.VISIBLE
            stopImageView.visibility = View.INVISIBLE
        }
        playImageView.setOnClickListener {
            exoPlayerManager!!.play()
            playImageView.visibility = View.INVISIBLE
            stopImageView.visibility = View.VISIBLE
        }
        stopImageView.setOnClickListener {
            exoPlayerManager!!.stopPlay()
            playImageView.visibility = View.VISIBLE
            stopImageView.visibility = View.INVISIBLE

        }

        songSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            var progress = 0
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                progress = p1
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                    exoPlayerManager!!.seekTo(progress)
                }
        })
        nextImageView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var nextIndex = ManageCurrentPlaySongList.getCurrentIndex()!! + 1
                if (nextIndex < ManageCurrentPlaySongList.playList.list.size) {
                    ManageCurrentPlaySongList.setCurrentIndex(nextIndex)
                    exoPlayerManager!!.startPlay(ManageCurrentPlaySongList.playList.list[nextIndex].source, true)
                    songPictureTextView.text = dealSongName(ManageCurrentPlaySongList.getCurrentSongName())
                    songPictureTextView.setTextColor(Color.WHITE)
                }
            }
        })
        preImageView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var preIndex = ManageCurrentPlaySongList.getCurrentIndex()!! - 1
                if (preIndex >= 0) {
                    ManageCurrentPlaySongList.setCurrentIndex(preIndex)
                    exoPlayerManager!!.startPlay(ManageCurrentPlaySongList.playList.list[preIndex].source, true)
                    dealSongPicture()
                }
            }
        })
        songNameTextView.text = ManageCurrentPlaySongList.getCurrentSongName()
        ManageCurrentPlaySongList.addRegisterChangeSongName(object : ManageCurrentPlaySongList.ChangeData {
            override fun changeData(songName: String) {
                songNameTextView.text = songName
                dealSongPicture()
            }
        })
        backImageView.setOnClickListener {
            this.onBackPressed()
        }
        when (MusicPlayConfigure.mode) {
            RepeatMode.RepeatAllAlbum -> {
                repeatImageView.setImageResource(R.drawable.ic_repeat_green_24dp)
            }
            RepeatMode.RepeatSingle -> {
                repeatImageView.setImageResource(R.drawable.ic_repeat_one_song_green_24dp)
            }
        }
        repeatImageView.setOnClickListener {
            when (MusicPlayConfigure.mode) {
                RepeatMode.NoRepeat -> {
                    repeatImageView.setImageResource(R.drawable.ic_repeat_green_24dp)
                    MusicPlayConfigure.mode = RepeatMode.RepeatAllAlbum
                }
                RepeatMode.RepeatAllAlbum -> {

                    repeatImageView.setImageResource(R.drawable.ic_repeat_one_song_green_24dp)
                    MusicPlayConfigure.mode = RepeatMode.RepeatSingle

                }
                RepeatMode.RepeatSingle -> {
                    repeatImageView.setImageResource(R.mipmap.ic_launcher_repeat_list)
                    MusicPlayConfigure.mode = RepeatMode.NoRepeat
                }
            }
        }


        randomImageView.setOnClickListener {
            MusicPlayConfigure.isRandom = (!MusicPlayConfigure.isRandom)
            if (MusicPlayConfigure.isRandom) {
                randomImageView.setImageResource(R.drawable.ic_lanch_random_active_green)
            } else {
                randomImageView.setImageResource(R.mipmap.ic_launcher_random)
            }
        }
        if (MusicPlayConfigure.isRandom) {
            randomImageView.setImageResource(R.drawable.ic_lanch_random_active_green)
        } else {
            randomImageView.setImageResource(R.mipmap.ic_launcher_random)
        }
        dealSongPicture()
    }


    fun dealStringSecond(second: String): String {
        if (second.length == 1) {
            return "0" + second
        }
        return second
    }

    fun dealSongName(songName: String): String {
        if (songName.contains("-")) {
            return songName.substring(4, 12)
        }
        return songName
    }

    fun dealSongPicture() {
        songPictureTextView.text = dealSongName(ManageCurrentPlaySongList.getCurrentSongName())
        songPictureTextView.setTextColor(Color.WHITE)
    }
}