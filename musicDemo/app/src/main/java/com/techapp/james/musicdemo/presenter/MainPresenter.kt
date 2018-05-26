package com.techapp.james.musicdemo.presenter

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.ImageView
import com.techapp.james.musicdemo.view.main.MainActivity
import com.techapp.james.musicdemo.view.musicView.MusicPlayActivity
import com.techapp.james.musicdemo.R
import com.techapp.james.musicdemo.model.musicModel.SongConfigure
import com.techapp.james.musicdemo.model.musicModel.ManageCurrentPlaySongList
import com.techapp.james.musicdemo.model.musicModel.PlayList
import com.techapp.james.musicdemo.model.musicModel.PlayLists
import com.techapp.james.musicdemo.model.musicModel.phoneMusicData.OriginMusicData
import com.techapp.james.musicdemo.model.playManager.Configure
import com.techapp.james.musicdemo.model.playManager.ExoPlayerManager
import com.techapp.james.musicdemo.model.preferenceModel.DBManger
import com.techapp.james.musicdemo.view.fragmentPage.fragmentRestart.ReStart
import com.techapp.james.musicdemo.view.main.BottomImage
import com.techapp.james.musicdemo.view.fragmentPage.firstFragment.FirstFragment
import com.techapp.james.musicdemo.view.fragmentPage.playlistFragment.PlayListFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

/**
 * Created by James on 2018/3/20.
 */
class MainPresenter {
    private var activity: MainActivity
    private val bottomImageList = ArrayList<BottomImage>()
    private val fragmentList = ArrayList<ReStart>();
    private var exoPlayerManager = ExoPlayerManager
    private var playList: PlayList? = null

    constructor(activity: MainActivity) {
        this.activity = activity
        bottomImageList.add(BottomImage(activity.homeImageView, R.drawable.ic_home_grey_white, R.drawable.ic_home_white))
        bottomImageList.add(BottomImage(activity.myMusicImageView, R.drawable.ic_folder_grey_white, R.drawable.ic_folder_white))
        fragmentList.add(PlayListFragment.getInstance() as ReStart)
        fragmentList.add(FirstFragment.getInstance() as ReStart)
    }

    fun handleMessage(msg: Message?) {
        var bundle = msg!!.data
        var currentPosition = bundle.get(Configure.currentPosition).toString().toInt()
        var maxTime = bundle.get(Configure.maxTime).toString().toInt()
        activity.bottomProgressBar.setMax(maxTime)
        activity.bottomProgressBar.setProgress(currentPosition)
    }

    fun isPlayJudge() { //In this function. When Exoplayer play view will display stop btn.
        if (exoPlayerManager.isPlay()) {
            activity.playImageView.visibility = View.INVISIBLE
            activity.stopImageView.visibility = View.VISIBLE
        } else {
            activity.playImageView.visibility = View.VISIBLE
            activity.stopImageView.visibility = View.INVISIBLE
        }
    }

    fun judgePlayNotification() {//In this function. When Exoplayer play view will display play btn.
        if (!exoPlayerManager.isPlay()) {
            activity.playImageView.visibility = View.INVISIBLE
            activity.stopImageView.visibility = View.VISIBLE
        } else {
            activity.playImageView.visibility = View.VISIBLE
            activity.stopImageView.visibility = View.INVISIBLE
        }
    }

    fun playImageClick() {
        exoPlayerManager.play()
        activity.playImageView.visibility = View.INVISIBLE
        activity.stopImageView.visibility = View.VISIBLE
    }

    fun stopImageClick() {
        exoPlayerManager.stopPlay()
        activity.playImageView.visibility = View.VISIBLE
        activity.stopImageView.visibility = View.INVISIBLE
    }

    fun endSingleMusicBottomBtn() {
        activity.playImageView.visibility = View.VISIBLE
        activity.stopImageView.visibility = View.INVISIBLE
    }

    fun playerLayoutClick() {
        if (ManageCurrentPlaySongList.currentIndex != SongConfigure.outOfBounds) {
            var intentMusic = Intent(activity, MusicPlayActivity::class.java)
            activity.startActivity(intentMusic)
        }
    }

    fun homeImageClick(image: ImageView) {
        var fragmentTransaction = activity.supportFragmentManager.beginTransaction()
        var firstFragment = FirstFragment.getInstance()
        setManageCurrentPlayListDataChange()

        fragmentTransaction.replace(R.id.frameLayout, firstFragment)
        fragmentTransaction.addToBackStack("first");
        fragmentTransaction.commit()
        onBottomClick(image)
    }

    fun onRandomEvent() {
        (fragmentList[1] as FirstFragment).randomSongDisplay()
    }

    fun onPlayListCurrentSongChangeColorEvent() {
        (fragmentList[1] as FirstFragment).onPlayListCurrentSongChangeColorEvent()
    }

    fun setManageCurrentPlayListDataChange() {
        ManageCurrentPlaySongList.addRegisterChangeSongName(object : ManageCurrentPlaySongList.ChangeData {
            override fun changeData(songName: String) {
                isPlayJudge()
                activity.songTextView.text = songName
            }
        })
    }

    fun myMusicImageViewClick(image: ImageView) {
        var fragmentTransaction = activity.supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, PlayListFragment.getInstance())
        fragmentTransaction.addToBackStack("first");
        fragmentTransaction.commit()//commitAllowingStateLoss 不會使程式崩潰 但是可能會使UI資訊錯誤
        onBottomClick(image)
    }

    fun onBottomClick(image: ImageView) {
        for (i in 0..(bottomImageList.size - 1)) {
            if (bottomImageList[i].equal(image)) {
                bottomImageList[i].setOnClickBackground()
            } else {
                bottomImageList[i].setNotClickBackground()
            }
        }
    }

    fun onRestart() {
        for (i in 0..(fragmentList.size - 1)) {
            fragmentList[i].onRestart();
        }
    }

    fun storePlayLists() {
        DBManger.instance.saveSongLists(PlayLists, activity.applicationContext)
    }

    fun getPlayLists() {
        var playListsData = DBManger.instance.getSongLists(activity.applicationContext)
        if (playListsData != null) {
            PlayLists.instance = playListsData
        }
    }

    fun setHandler(mainHandler: Handler, bottomHandler: Handler) {
        exoPlayerManager.setMainHandler(mainHandler)
        exoPlayerManager.setUIHandler(bottomHandler)
    }

    fun setPlayListOriginData(context: Context) {
        Configure.context = context
        if (ManageCurrentPlaySongList.playList.list.size == 0) {
            playList = OriginMusicData.getMusicFromPhone(Configure.context!!)
            ManageCurrentPlaySongList.playList = this.playList!!
        }
    }
}