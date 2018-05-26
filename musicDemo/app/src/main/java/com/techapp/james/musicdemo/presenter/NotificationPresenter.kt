package com.techapp.james.musicdemo.presenter

import android.app.Activity
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.widget.RemoteViews
import com.techapp.james.musicdemo.R
import com.techapp.james.musicdemo.model.musicModel.ManageCurrentPlaySongList
import com.techapp.james.musicdemo.model.playManager.ExoPlayManagerSubject
import com.techapp.james.musicdemo.model.playManager.ExoPlayerManager
import com.techapp.james.musicdemo.view.musicView.MusicPlayActivity

/**
 * Created by James on 2018/4/4.
 */
class NotificationPresenter : ManageCurrentPlaySongList.ChangeData, ExoPlayManagerSubject {
    var activity: Activity
    var notificationBuilder: NotificationCompat.Builder? = null
    var notificationManager: NotificationManager? = null
    var notification_id: Int = 3
    var remoteViews: RemoteViews? = null

    companion object {
        private var instances: NotificationPresenter? = null
        fun getInstance(activity: Activity?): NotificationPresenter? {
            if (instances == null && activity != null) {
                instances = NotificationPresenter(activity)
            }
            return instances
        }
    }

    private constructor(activity: Activity) {
        this.activity = activity
    }

    fun showNotification() {
        notificationManager = this.activity.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationBuilder = NotificationCompat.Builder(activity.applicationContext)
        remoteViews = RemoteViews(activity.packageName, R.layout.presenter_notification_layout)
        //   remoteViews.setTextViewText(R.id.test,ManageCurrentPlaySongList.getCurrentSongName())
        remoteViews!!.setTextViewText(R.id.songNameTextView, ManageCurrentPlaySongList.getCurrentSongName())
//button click-------------
        var intent = Intent("com.techapp.james.ACTION_PLAY")
        var pendingIntent: PendingIntent = PendingIntent.getBroadcast(activity.applicationContext, 100, intent, 0)
        remoteViews!!.setOnClickPendingIntent(R.id.playStopSongImageView, pendingIntent)
        intent = Intent("com.techapp.james.ACTION_NEXT")
        pendingIntent = PendingIntent.getBroadcast(activity.applicationContext, 100, intent, 0)
        remoteViews!!.setOnClickPendingIntent(R.id.nextSongImageView, pendingIntent)
        intent = Intent("com.techapp.james.ACTION_PREVIOUS")
        //requestCode is used to retrieve the same pending intent instance later on (for cancelling, etc).
        //flag https://developer.android.com/reference/android/app/PendingIntent.html
        pendingIntent = PendingIntent.getBroadcast(activity.applicationContext, 100, intent, 0)
        remoteViews!!.setOnClickPendingIntent(R.id.preSongImageView, pendingIntent)
//-----
        //set button image--
        remoteViews!!.setImageViewResource(R.id.preSongImageView, R.drawable.ic_previous_black_24dp)
        remoteViews!!.setImageViewResource(R.id.playStopSongImageView, R.drawable.ic_play_arrow_black_24dp)
        remoteViews!!.setImageViewResource(R.id.nextSongImageView, R.drawable.ic_next_black_24dp)
//--
        intent = Intent()
        intent.setClass(activity, MusicPlayActivity::class.java)
        pendingIntent = PendingIntent.getActivity(activity.applicationContext, notification_id, intent, 0)
        notificationBuilder!!.setSmallIcon(R.drawable.music_notification_icon)
                .setAutoCancel(false)
                .setOngoing(true) //Let's notifiaction not disappear
                .setCustomContentView(remoteViews)
                .setContentIntent(pendingIntent)
        //.setCustomBigContentView()
        customerNotification = notificationBuilder!!.build()
        notificationManager!!.notify(notification_id, customerNotification)
    }

    fun cancelNotification() {
        notificationManager!!.cancel(notification_id)
    }

    override fun changeData(songName: String) {
        remoteViews!!.setTextViewText(R.id.songNameTextView, songName)
        notificationManager!!.notify(notification_id, notificationBuilder!!.build());
    }

    var customerNotification: Notification? = null
    override fun updateExoPlayerState() {
        if (ExoPlayerManager.isPlay()) {
            remoteViews!!.setImageViewResource(R.id.playStopSongImageView, R.drawable.ic_play_arrow_black_24dp)
        } else {
            remoteViews!!.setImageViewResource(R.id.playStopSongImageView, R.drawable.ic_pause_black_24dp)
        }
        notificationManager!!.notify(notification_id, notificationBuilder!!.build());
    }

    fun onPlayBtnState() {
        remoteViews!!.setImageViewResource(R.id.playStopSongImageView, R.drawable.ic_play_arrow_black_24dp)
        notificationManager!!.notify(notification_id, notificationBuilder!!.build());
    }

    fun onStopBtnState() {
        remoteViews!!.setImageViewResource(R.id.playStopSongImageView, R.drawable.ic_pause_black_24dp)
        notificationManager!!.notify(notification_id, notificationBuilder!!.build());
    }
}