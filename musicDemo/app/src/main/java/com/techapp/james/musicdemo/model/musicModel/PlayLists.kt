package com.techapp.james.musicdemo.model.musicModel

/**
 * Created by James on 2018/3/29.
 */
class PlayLists : ArrayList<PlayList>() {
    companion object {
        var instance = PlayLists()
    }
}