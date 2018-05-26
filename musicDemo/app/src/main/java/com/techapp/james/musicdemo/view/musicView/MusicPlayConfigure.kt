package com.techapp.james.musicdemo.view.musicView

/**
 * Created by James on 2018/3/20.
 */
object MusicPlayConfigure {
    var isLoop = false
    var isRandom = false
    var mode=RepeatMode.NoRepeat
}

enum class RepeatMode {
    NoRepeat,RepeatAllAlbum,RepeatSingle
}