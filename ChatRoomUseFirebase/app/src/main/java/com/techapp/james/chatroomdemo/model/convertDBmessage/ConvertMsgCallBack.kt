package com.techapp.james.chatroomdemo.model.convertDBmessage

import com.github.bassaer.chatmessageview.model.Message


/**
 * Created by James on 2018/4/24.
 */
interface ConvertMsgCallBack {
    fun convertCallBack(message: Message)
}