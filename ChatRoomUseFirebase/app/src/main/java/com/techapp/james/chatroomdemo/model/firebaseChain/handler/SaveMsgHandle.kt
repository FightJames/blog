package com.techapp.james.chatroomdemo.model.firebaseChain.handler

import com.techapp.james.chatroomdemo.model.firebaseChain.firebaseDBObject.messageObject.MessageData
import com.techapp.james.firebasechainofresponsibility.model.firebaseChain.FirebaseManager

/**
 * Created by James on 2018/4/17.
 */
class SaveMsgHandle : FirebaseManager {
    constructor()

    override fun handle(any: Any?) {
            var messageData = any as MessageData
            val myRef = database.getReference("Group/"+messageData.id)
            myRef.setValue(messageData)
    }
}