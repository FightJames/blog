package com.techapp.james.chatroomdemo.model.firebaseChain.handler

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.techapp.james.chatroomdemo.model.firebaseChain.firebaseDBObject.messageObject.MessageData
import com.techapp.james.firebasechainofresponsibility.model.firebaseChain.FirebaseManager

class GetLastMsg : FirebaseManager {
    constructor()

    override fun handle(any: Any?) {
        var inputMsg = any as MessageData
        val myRef = database.getReference("Group")
        var time = inputMsg.time.toLong() + 1
        var filterRef = myRef.orderByKey().startAt(time.toString()).limitToFirst(5)
        filterRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                var iterator = dataSnapshot!!.children.iterator()
                var msg: MessageData? = null
                while (iterator.hasNext()) {
                    val value = iterator.next()
                    msg = value.getValue(MessageData::class.java)
                    if (!inputMsg!!.time.equals(msg!!.time)) {
                        Log.d("LastMsg", inputMsg.time.toString() + " db " + msg!!.content)
                        next!!.handle(msg)
                    }
                }
//                msg = MessageData()
//                msg!!.isLast = true
//                next!!.handle(msg)
            }
        })
    }
//endAt()

}