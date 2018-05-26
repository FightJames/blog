package com.techapp.james.chatroomdemo.presenter

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import com.techapp.james.chatroomdemo.R
import com.techapp.james.chatroomdemo.model.userModel.CurrentUser
import com.techapp.james.chatroomdemo.model.userModel.User
import com.techapp.james.chatroomdemo.view.chatRoomView.ChatRoomActivity
import com.techapp.james.chatroomdemo.view.SignUpActivity
import com.techapp.james.firebasechainofresponsibility.model.firebaseChain.DBCallBack
import com.techapp.james.firebasechainofresponsibility.model.firebaseChain.FirebaseFacade

/**
 * Created by James on 2018/4/16.
 */
class MainPresenter {
    var activity: Activity
    var firebaseFacade = FirebaseFacade

    constructor(activity: Activity) {
        this.activity = activity
    }

    fun updateUI(user: FirebaseUser?) {
        if (user == null) {
            val toast = Toast.makeText(activity,
                    "Login Fail", Toast.LENGTH_LONG)
            toast.show()
            return
        }
        CurrentUser.fireBaseUser=user
        val defaultIcon = BitmapFactory.decodeResource(activity.resources, R.drawable.default_icon)

        var u = User(user.uid, user.email!!, defaultIcon,user.email!!)

        firebaseFacade.getUser(CurrentUser.fireBaseUser!!.uid, object : DBCallBack {
            override fun dbCallBack(any: Any) {
                var dbUser = any as User
                if (dbUser.myId == user.uid) {
                    CurrentUser.dbUser = dbUser
                    var i = Intent(activity, ChatRoomActivity::class.java)
                    activity.startActivity(i)
                } else {
                    CurrentUser.dbUser = u
                    firebaseFacade.saveUser(u)
                    var i = Intent(activity, ChatRoomActivity::class.java)
                    activity.startActivity(i)
                }
            }
        })
    }

    fun intentToSignUp() {
        var i = Intent(activity, SignUpActivity::class.java)
        activity.startActivity(i)
    }
}