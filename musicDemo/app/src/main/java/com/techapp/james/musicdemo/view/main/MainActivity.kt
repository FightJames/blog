package com.techapp.james.musicdemo.view.main


import android.Manifest
import android.content.Intent
import android.os.*
import android.support.v7.app.AppCompatActivity
import com.techapp.james.musicdemo.R
import com.techapp.james.musicdemo.model.musicModel.ManageCurrentPlaySongList
import com.techapp.james.musicdemo.model.threadModel.ThreadConfig
import com.techapp.james.musicdemo.presenter.MainPresenter
import com.techapp.james.musicdemo.view.fragmentPage.firstFragment.FirstFragment
import kotlinx.android.synthetic.main.activity_main.*
import android.content.pm.PackageManager
import android.support.annotation.RequiresApi
import android.util.Log
import com.techapp.james.musicdemo.model.notificationAndMusicService.LaunchFlag
import com.techapp.james.musicdemo.model.notificationAndMusicService.MusicService
import com.techapp.james.musicdemo.model.playManager.ExoPlayManagerSubject
import com.techapp.james.musicdemo.model.playManager.ExoPlayerManager
import com.techapp.james.musicdemo.view.fragmentPage.OnFragmentInteration
import com.techapp.james.musicdemo.view.musicView.MusicPlayActivity
import java.util.ArrayList


class MainActivity : AppCompatActivity(), ExoPlayManagerSubject, OnFragmentInteration {
    override fun onFragmentInteration(s: String) {
        when (s) {
            this.getString(R.string.fragmentOne) -> {
                mainPresenter!!.onBottomClick(0)
            }
            this.getString(R.string.fragmentSecond) -> {
                mainPresenter!!.onBottomClick(1)
            }
        }
    }

    var mainPresenter: MainPresenter? = null
    var uiHandler: Handler? = null
    var mainHandler: Handler? = null
    private val allPermission = ArrayList<Int>()
    private val permission = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)  //權限項目在此新增
    private val REQUEST_CODE_ASK_PERMISSIONS = 0
    //    var notificationPresenter: NotificationPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intentMusicActivity(intent)

        ExoPlayerManager.registSubject(this)
        //--
        startService(Intent(this, MusicService::class.java))
        setContentView(R.layout.activity_main)
        mainHandler = Handler(Looper.getMainLooper())
        init()//create presenter here
        mainPresenter!!.getPlayLists()
        if (applyRight()) {
            mainPresenter!!.setPlayListOriginData(applicationContext)
        }
        songTextView.text = ManageCurrentPlaySongList.getCurrentSongName()
        var fragmentTransaction = supportFragmentManager.beginTransaction()
        var firstFragment = FirstFragment.getInstance()
        ManageCurrentPlaySongList.addRegisterChangeSongName(object : ManageCurrentPlaySongList.ChangeData {
            override fun changeData(songName: String) {
                songTextView.text = songName
                mainPresenter!!.isPlayJudge()
                mainPresenter!!.onPlayListCurrentSongChangeColorEvent()
            }
        })

        fragmentTransaction.replace(R.id.frameLayout, firstFragment)
        fragmentTransaction.commit()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intentMusicActivity(intent)
    }

    override fun updateExoPlayerState() {
        Log.d("MainActivity", "ExoPlayerUpdate")
        mainPresenter!!.judgePlayNotification()
    }

    override fun onBackPressed() {
        var fragments = this
                .getSupportFragmentManager()
        if (!fragments.popBackStackImmediate()) {
            var intentHome = Intent(Intent.ACTION_MAIN)
            intentHome.addCategory(Intent.CATEGORY_HOME)
            intentHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            this.startActivity(intentHome)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        judgeRightDoNext(requestCode, permissions, grantResults)
        var fragmentTransaction = supportFragmentManager.beginTransaction()
        var firstFragment = FirstFragment.getInstance()
        mainPresenter!!.setManageCurrentPlayListDataChange()
        fragmentTransaction.detach(firstFragment)
        fragmentTransaction.attach(firstFragment)
        //fragmentTransaction.replace(R.id.frameLayout, firstFragment)
        fragmentTransaction.commit()

    }


    private fun init() {
        mainPresenter = MainPresenter(this)
        mainPresenter!!.setManageCurrentPlayListDataChange()

        uiHandler = object : Handler() {
            override fun handleMessage(msg: Message?) {
                when (msg!!.what) {
                    ThreadConfig.PROGRESSBAR_MSG -> {
                        mainPresenter!!.handleMessage(msg)
                    }
                    ThreadConfig.PLAY_BTN_MSG -> {
                        mainPresenter!!.endSingleMusicBottomBtn()
                    }
                    ThreadConfig.RANDOM_MSG -> {
                        songTextView.text = ManageCurrentPlaySongList.getCurrentSongName()
                        mainPresenter!!.onRandomEvent()
                    }
                    ThreadConfig.PLAYLIST_UPDATE_MSG -> {
                        songTextView.text = ManageCurrentPlaySongList.getCurrentSongName()
                        mainPresenter!!.onPlayListCurrentSongChangeColorEvent()
                    }
                }
            }
        }

        playImageView.setOnClickListener {
            mainPresenter!!.playImageClick()
        }
        stopImageView.setOnClickListener {
            mainPresenter!!.stopImageClick()
        }
        playerLayout.setOnClickListener {
            mainPresenter!!.playerLayoutClick()
        }
        homeImageView.setOnClickListener {
            mainPresenter!!.homeImageClick(homeImageView)//commitAllowingStateLoss 不會使程式崩潰 但是可能會使UI資訊錯誤
        }
        myMusicImageView.setOnClickListener {
            mainPresenter!!.myMusicImageViewClick(myMusicImageView)
        }
        mainPresenter!!.setHandler(mainHandler!!, uiHandler!!)
        mainPresenter!!.isPlayJudge()
        songTextView.text = ManageCurrentPlaySongList.getCurrentSongName()
    }

    override fun onStop() {
        // Log.d("MainActivity","onStop")
        super.onStop()
        mainPresenter!!.storePlayLists()
    }

    override fun onRestart() {
        //  Log.d("MainActivity","onRestart")
        super.onRestart()
        mainPresenter!!.onRestart()
        init()
    }

    override fun onDestroy() {
        mainPresenter!!.storePlayLists()
        stopService(Intent(this, MusicService::class.java))
        super.onDestroy()
    }

    fun judgeRightDoNext(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        var judge = true
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            for (i in 0..(grantResults.size - 1)) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                } else {
                    judge = false
                }
            }
        }
        if (judge) {
            mainPresenter!!.setPlayListOriginData(applicationContext)
        } else {
            this.finish()
        }
    }


    fun intentMusicActivity(i: Intent?) {
        i?.let {
            if (it.getBooleanExtra(LaunchFlag.isDisplayMusicView, false)) {
                startActivity(Intent(this, MusicPlayActivity::class.java))
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun applyRight(): Boolean {
        for (i in permission.indices) {
            val eachPermission = this.checkSelfPermission(permission[i]) // Do you have right
            allPermission.add(eachPermission)
        }
        for (i in allPermission.indices) {
            if (allPermission[i] != PackageManager.PERMISSION_GRANTED) {
                // System.out.println(permission[serviceIntent]);
                this.requestPermissions(permission,
                        REQUEST_CODE_ASK_PERMISSIONS)
                return false
            }
        }
        return true
    }
}