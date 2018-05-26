package com.techapp.james.firebasechainofresponsibility.model.firebaseChain

import com.techapp.james.chatroomdemo.model.firebaseChain.firebaseDBObject.messageObject.MessageData
import com.techapp.james.chatroomdemo.model.userModel.CurrentUser

/**
 * Created by James on 2018/4/17.
 */
class FiltMsgHandle : FirebaseManager {
    constructor()

    override fun handle(any: Any?) {

//            val messages = any as Messages
//            var notDisplay = CurrentUser.dbUser!!.notDisplay
//            var removes = ArrayList<MessageData>()
//            if (notDisplay != null) {
//                for (i in 0..(messages.size - 1)) {
//                    for (i in 0..(notDisplay.size - 1))
//                        if (messages[i].id.equals(notDisplay[i])) {
//                            removes.add(messages[i])
//                        }
//                }
//            }
//            for (i in 0..(removes.size - 1)) {
//                messages.remove(removes[i])
//            }
//
//            for (i in 0..(messages.size - 1)) {
//                Log.d("TAG", "Result Key is " + messages[i].id + " Value is: " + messages[i].content)
//            }
        val message = any as MessageData
        var notDisplay = CurrentUser.dbUser!!.notDisplay
        var flag = true
        if (notDisplay != null) {
            for (i in 0..(notDisplay.size - 1))
                if (message.id.equals(notDisplay[i])) {
                    flag = false
                }
        }
        if (flag) {
            dbCallBack.dbCallBack(message)
        }
        //  next.handle(messages)
    }

}
