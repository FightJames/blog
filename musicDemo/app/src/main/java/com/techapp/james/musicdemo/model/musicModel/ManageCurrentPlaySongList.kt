package com.techapp.james.musicdemo.model.musicModel

import com.techapp.james.musicdemo.model.musicModel.exceptionOutOfBound.RemoveMusicException
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by James
 * on 2018/3/19.
 */
object ManageCurrentPlaySongList {
    var playList: PlayList
    var currentIndex: Int
    private val random = Random()
    private var observers: ArrayList<ChangeData>

    init {
        observers = ArrayList()
        playList = PlayList()
        currentIndex = SongConfigure.outOfBounds
    }

    fun addRegisterChangeSongName(input: ChangeData) {
        observers.add(input)
    }

    fun add(song: Song) {
        playList.list.add(song)
    }

    fun clear() {
        playList.clear()

    }

    fun getCurrentIndex(): Int? {
        return currentIndex
    }

    fun getRandomSong(): Song {
        if (currentIndex != SongConfigure.outOfBounds) {
            currentIndex = random.nextInt(0..playList.list.size) //not include playList.size
            notifyCurrentSongNameAndPlayBtn()
            return playList.list[currentIndex]
        } else {
            throw RemoveMusicException()
        }
    }

    fun getCurrentSong(): Song {
        if (currentIndex != SongConfigure.outOfBounds) {
            return playList.list[currentIndex]
        } else {
            throw RemoveMusicException()
        }
    }

    fun notifyCurrentSongNameAndPlayBtn() {
        for (i in 0..(observers.size - 1)) {
            observers.get(i).changeData(getCurrentSongName())
        }
    }

    fun setCurrentIndex(currentIndex: Int?) {
        this.currentIndex = currentIndex!!
        if (currentIndex!! >= playList.list.size) {
            this.currentIndex = SongConfigure.outOfBounds
        }
        notifyCurrentSongNameAndPlayBtn()
    }

    fun getCurrentSongName(): String {
        if (currentIndex != SongConfigure.outOfBounds && currentIndex < playList.list.size) {
            return playList.list[currentIndex].title
        }
        return "SongName"
    }


    private fun Random.nextInt(range: IntRange): Int {
        return range.start + nextInt(range.last - range.start)
    }

    interface ChangeData {
        fun changeData(songName: String)
    }
}