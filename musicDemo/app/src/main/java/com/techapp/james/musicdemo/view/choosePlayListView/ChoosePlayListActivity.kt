package com.techapp.james.musicdemo.view.choosePlayListView

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.techapp.james.musicdemo.R
import com.techapp.james.musicdemo.model.musicModel.PlayLists
import com.techapp.james.musicdemo.view.createPlaylistView.CreatePlayListActivity
import kotlinx.android.synthetic.main.choose_playlist_view_activity_choose_play_list.*

class ChoosePlayListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_playlist_view_activity_choose_play_list)
        playListRecycler.layoutManager = LinearLayoutManager(this)
        playListRecycler.adapter = ChoosePlayListRecyclerAdapter(this, PlayLists.instance)
        backImageView.setOnClickListener {
            this.finish()
        }
        newPlayListbtn.setOnClickListener {
            var i = Intent()
            i.setClass(this, CreatePlayListActivity::class.java)
            startActivity(i)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        playListRecycler.adapter = null
    }
}
