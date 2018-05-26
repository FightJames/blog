package com.techapp.james.musicdemo.model.musicModel

/**
 * Created by James on 2018/3/29.
 */
class PlayList {
    var list: ArrayList<Song>
        private set(value) {
            field = value
        }
        public get() = field
    var name: String = ""
    var imageUri = ""

    constructor(name: String) {
        list = ArrayList<Song>()
        this.name = name
    }

    constructor() {
        list = ArrayList<Song>()
        name = "#No Name"
    }

    fun clear() {
        list.clear()
        name = ""
    }
}