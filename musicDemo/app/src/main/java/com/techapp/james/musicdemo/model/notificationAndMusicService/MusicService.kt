package com.techapp.james.musicdemo.model.notificationAndMusicService

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.widget.RemoteViews
import com.techapp.james.musicdemo.R
import com.techapp.james.musicdemo.model.musicModel.ManageCurrentPlaySongList
import com.techapp.james.musicdemo.model.playManager.ExoPlayManagerSubject
import com.techapp.james.musicdemo.model.playManager.ExoPlayerManager
import com.techapp.james.musicdemo.view.main.MainActivity
import com.techapp.james.musicdemo.view.musicView.MusicPlayActivity

class MusicService : Service(), ManageCurrentPlaySongList.ChangeData, ExoPlayManagerSubject {
    lateinit var player: ExoPlayerManager
    lateinit var remoteViews: RemoteViews
    lateinit var notificationManager: NotificationManager
    lateinit var notificationBuilder: NotificationCompat.Builder
    var notification_id: Int = 3
    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        player = ExoPlayerManager
        notificationManager = this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationBuilder = NotificationCompat.Builder(this.applicationContext)
        remoteViews = RemoteViews(this.packageName, R.layout.presenter_notification_layout)
        //   remoteViews.setTextViewText(R.id.test,ManageCurrentPlaySongList.getCurrentSongName())
        remoteViews!!.setTextViewText(R.id.songNameTextView, ManageCurrentPlaySongList.getCurrentSongName())
//button click-------------
        var intent = Intent("com.techapp.james.ACTION_PLAY")
        var pendingIntent: PendingIntent = PendingIntent.getBroadcast(this.applicationContext, 100, intent, 0)
        remoteViews!!.setOnClickPendingIntent(R.id.playStopSongImageView, pendingIntent)
        intent = Intent("com.techapp.james.ACTION_NEXT")
        pendingIntent = PendingIntent.getBroadcast(this.applicationContext, 100, intent, 0)
        remoteViews!!.setOnClickPendingIntent(R.id.nextSongImageView, pendingIntent)
        intent = Intent("com.techapp.james.ACTION_PREVIOUS")
        //requestCode is used to retrieve the same pending intent instance later on (for cancelling, etc).
        //flag https://developer.android.com/reference/android/app/PendingIntent.html
        pendingIntent = PendingIntent.getBroadcast(this.applicationContext, 100, intent, 0)
        remoteViews!!.setOnClickPendingIntent(R.id.preSongImageView, pendingIntent)
//-----
        //set button image--
        remoteViews!!.setImageViewResource(R.id.preSongImageView, R.drawable.ic_previous_black_24dp)
        remoteViews!!.setImageViewResource(R.id.playStopSongImageView, R.drawable.ic_play_arrow_black_24dp)
        remoteViews!!.setImageViewResource(R.id.nextSongImageView, R.drawable.ic_next_black_24dp)
//--
        intent = Intent()
        intent.setClass(this, MainActivity::class.java)
        intent.putExtra(LaunchFlag.isDisplayMusicView, true)
        pendingIntent = PendingIntent.getActivity(this.applicationContext, notification_id, intent, 0)
        notificationBuilder!!.setSmallIcon(R.drawable.music_notification_icon)
                .setAutoCancel(false)
                .setOngoing(true) //Let's notifiaction not disappear
                .setCustomContentView(remoteViews)
                .setContentIntent(pendingIntent)
        //.setCustomBigContentView()
        customerNotification = notificationBuilder!!.build()
        ExoPlayerManager.registSubject(this)
        ManageCurrentPlaySongList.addRegisterChangeSongName(this)
        startForeground(1, customerNotification)
//        notificationManager!!.notify(notification_id, customerNotification)
    }

    override fun onDestroy() {
        ExoPlayerManager.releasePlay()
        super.onDestroy()
    }

    override fun changeData(songName: String) {
        remoteViews!!.setTextViewText(R.id.songNameTextView, songName)
        startForeground(1, customerNotification)
    }

    var customerNotification: Notification? = null
    override fun updateExoPlayerState() {
        if (ExoPlayerManager.isPlay()) {
            remoteViews!!.setImageViewResource(R.id.playStopSongImageView, R.drawable.ic_play_arrow_black_24dp)
        } else {
            remoteViews!!.setImageViewResource(R.id.playStopSongImageView, R.drawable.ic_pause_black_24dp)
        }
        startForeground(1, customerNotification)
    }
}
