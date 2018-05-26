package com.techapp.james.firebasechainofresponsibility.model.firebaseChain

import com.techapp.james.chatroomdemo.model.firebaseChain.GetUserHandle
import com.techapp.james.chatroomdemo.model.firebaseChain.SaveUserHandle
import com.techapp.james.chatroomdemo.model.firebaseChain.firebaseDBObject.messageObject.MessageData
import com.techapp.james.chatroomdemo.model.firebaseChain.handler.GetLastMsg
import com.techapp.james.chatroomdemo.model.firebaseChain.handler.GetPastMsg
import com.techapp.james.chatroomdemo.model.firebaseChain.handler.SaveMsgHandle
import com.techapp.james.chatroomdemo.model.userModel.User


/**
 * Created by James on 2018/4/17.
 */
object FirebaseFacade {
    private var getChain: FirebaseManager
    private var getPastChain: FirebaseManager
    private var getLastChain: FirebaseManager

    init {
        var getMsgHandle = GetMsgHandle()
        var filtMsge = FiltMsgHandle()
        getMsgHandle.next = filtMsge
        getChain = getMsgHandle
        var getPastMsg = GetPastMsg()
        filtMsge = FiltMsgHandle()
        getPastMsg.next = filtMsge
        getPastChain = getPastMsg
        var getLastMsg = GetLastMsg()
        filtMsge = FiltMsgHandle()
        getLastMsg.next = filtMsge
        getLastChain = getLastMsg
    }

    // convert message is more complex than user, so I convert it in other model.
    fun getMsg(callBack: DBCallBack) {
        getChain.next!!.dbCallBack = callBack
        getChain.handle(null)
    }

    fun getPastMsg(msgData: MessageData, callBack: DBCallBack) {
        getPastChain.next!!.dbCallBack = callBack
        getPastChain.handle(msgData)
    }

    fun getLastMsg(msgData: MessageData, callBack: DBCallBack) {
        getLastChain.next!!.dbCallBack = callBack
        getLastChain.handle(msgData)
    }

    fun saveMsg(msgData: MessageData) {
        var saveMessage = SaveMsgHandle()
        saveMessage.handle(msgData)
    }

    // user convert is simple, so I convert it in chain.
    fun getUser(uid: String, callBack: DBCallBack) {
        var getUserHandle = GetUserHandle()
        getUserHandle.dbCallBack = callBack
        getUserHandle.handle(uid)
    }

    fun saveUser(user: User) {
        var saveUserHandle = SaveUserHandle()
        saveUserHandle.handle(user)
    }
}