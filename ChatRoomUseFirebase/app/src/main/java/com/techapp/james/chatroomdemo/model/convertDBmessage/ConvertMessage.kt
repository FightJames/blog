package com.techapp.james.chatroomdemo.model.convertDBmessage

import com.github.bassaer.chatmessageview.model.Message
import com.techapp.james.chatroomdemo.model.firebaseChain.firebaseDBObject.messageObject.MessageData
import com.techapp.james.chatroomdemo.model.userModel.CurrentUser
import com.techapp.james.chatroomdemo.model.userModel.User
import com.techapp.james.firebasechainofresponsibility.model.firebaseChain.DBCallBack
import com.techapp.james.firebasechainofresponsibility.model.firebaseChain.FirebaseFacade
import java.util.*

/**
 * Created by James on 2018/4/24.
 */
class ConvertMessage {

    fun convertSingleDBMsgToUIMsg(message: MessageData, convertCallBack: ConvertMsgCallBack) {
        FirebaseFacade.getUser(message.uid, object : DBCallBack {
            override fun dbCallBack(any: Any) {
                val calendar = Calendar.getInstance()
                calendar.setTimeInMillis(message.time.toLong())
                var msg = Message.Builder()
                        .setUser(any as User) // Sender
                        .setText(message.content) //Message contents
                        .setSendTime(calendar)
                        .build()
                convertCallBack.convertCallBack(msg)
            }
        })
    }

    fun convertUIMsgToDBMsg(msg: Message): MessageData {
        var ans: MessageData = MessageData()
        ans.time = msg.sendTime.timeInMillis.toString()
        ans.id = msg.sendTime.timeInMillis.toString()
        ans.uid = CurrentUser.dbUser!!.myId
        ans.content = msg.text!!.toString()
        return ans
    }
}