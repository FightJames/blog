package com.techapp.james.musicdemo.model.musicModel.phoneMusicData

import android.content.ContentResolver
import android.content.Context
import android.provider.MediaStore
import com.techapp.james.musicdemo.model.musicModel.Song
import com.techapp.james.musicdemo.model.musicModel.PlayList

/**
 * Created by James on 2018/3/29.
 */
object OriginMusicData {
    var musicResolver: ContentResolver? = null
    var originPlayList: PlayList?=null  //It will be set in beginning
    fun getMusicFromPhone(context: Context): PlayList {
        var playList: PlayList = PlayList()
        playList.name = "OriginData"
        musicResolver = context!!.contentResolver
        val musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val musicCursor = musicResolver!!.query(musicUri, null, null, null, null)
        if (musicCursor != null) {
            musicCursor.moveToFirst()
            while (musicCursor.moveToNext()) {
                var url = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                var title = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                var song = Song(url, title)
                playList.list.add(song)
//                val GENRE_ID = MediaStore.Audio.Genres._ID
//                val GENRE_NAME = MediaStore.Audio.Genres.NAME
//                val SONG_ID = android.provider.MediaStore.Audio.Media._ID
//                val SONG_TITLE = android.provider.MediaStore.Audio.Media.TITLE
//                val SONG_ARTIST = android.provider.MediaStore.Audio.Media.ARTIST
//                val SONG_ALBUM = android.provider.MediaStore.Audio.Media.ALBUM
//                val SONG_YEAR = android.provider.MediaStore.Audio.Media.YEAR
//                val SONG_TRACK_NO = android.provider.MediaStore.Audio.Media.TRACK
//                val SONG_FILEPATH = android.provider.MediaStore.Audio.Media.DATA
//                val SONG_DURATION = android.provider.MediaStore.Audio.Media.DURATION
            }
        }
        originPlayList=playList
        return playList
    }
}