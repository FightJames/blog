package com.techapp.james.chatroomdemo.model.userModel

import android.graphics.Bitmap
import com.github.bassaer.chatmessageview.model.IChatUser

/**
 * Created by James on 2018/4/13.
 */
class User : IChatUser {
    var myId: String = ""
    var myName: String = ""
    private var icon: Bitmap? = null
    var notDisplay = ArrayList<String>()
    var email: String = ""

    constructor() {}
    constructor(myId: String, myName: String, myIcon: Bitmap, email: String) {
        this.myId = myId
        this.myName = myName
        this.icon = myIcon
        this.email = email
    }


    override fun getIcon(): Bitmap? {
        return this.icon
    }

    override fun getId(): String {
        return this.myId
    }

    override fun getName(): String? {
        return this.myName
    }

    override fun setIcon(bmp: Bitmap) {
        this.icon = bmp
    }
}