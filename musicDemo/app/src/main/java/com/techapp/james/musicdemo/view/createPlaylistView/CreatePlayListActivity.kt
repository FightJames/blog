package com.techapp.james.musicdemo.view.createPlaylistView

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.techapp.james.musicdemo.R
import com.techapp.james.musicdemo.model.musicModel.PlayList
import com.techapp.james.musicdemo.model.musicModel.PlayLists
import kotlinx.android.synthetic.main.create_playlist_view_activity_create_play_list.*


class CreatePlayListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_playlist_view_activity_create_play_list)
        var click = false
        createTextView.setOnClickListener {
            if (!click&&(!playListNameEditText.text.toString().equals(""))) {
                var newPlayList = PlayList(playListNameEditText.text.toString())
                PlayLists.instance.add(newPlayList)
                Log.d("CreatePlayListActivity", playListNameEditText.text.toString())
                Log.d("CreatePlayListActivity", "list " + PlayLists.instance.get(0).name)
                this.finish()
            }
            click = true
        }
        cancelTextView.setOnClickListener {
            this.finish()
        }
    }
}
