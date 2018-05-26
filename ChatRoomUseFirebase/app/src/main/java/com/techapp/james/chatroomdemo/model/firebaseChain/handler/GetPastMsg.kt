package com.techapp.james.chatroomdemo.model.firebaseChain.handler

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.techapp.james.chatroomdemo.model.firebaseChain.firebaseDBObject.messageObject.MessageData
import com.techapp.james.firebasechainofresponsibility.model.firebaseChain.FirebaseManager

/**
 * Created by James on 2018/4/26.
 */
//next set FiltMessage
class GetPastMsg : FirebaseManager {
    constructor()

    override fun handle(any: Any?) {
        var msg = any as MessageData
        val myRef = database.getReference("Group")
        var time=msg.time.toLong()-1
        var filterRef = myRef.orderByKey().endAt(time.toString()).limitToLast(5)
        filterRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                var iterator = dataSnapshot!!.children.iterator()
                var msg:MessageData?=null
                while (iterator.hasNext()) {
                    val value = iterator.next()
                    msg= value.getValue(MessageData::class.java)
                    next!!.handle(msg)
                }
                msg= MessageData()
                msg!!.isLast=true
                next!!.handle(msg)
            }
        })
    }
//endAt()

}