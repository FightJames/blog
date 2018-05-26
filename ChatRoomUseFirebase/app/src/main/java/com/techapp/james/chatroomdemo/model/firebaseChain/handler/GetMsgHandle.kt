package com.techapp.james.firebasechainofresponsibility.model.firebaseChain

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.techapp.james.chatroomdemo.model.firebaseChain.firebaseDBObject.messageObject.MessageData

/**
 * Created by James on 2018/4/17.
 */
class GetMsgHandle : FirebaseManager() {

    override fun handle(any: Any?) {
        val myRef = database.getReference("Group")
        var filterRef = myRef.orderByKey().limitToLast(10)
        filterRef.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot?, p1: String?) {
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot?, p1: String?) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot!!.getValue(MessageData::class.java)
                value?.let { next!!.handle(value) }
            }

            override fun onChildRemoved(p0: DataSnapshot?) {
            }
        })
    }
}