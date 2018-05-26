package com.techapp.james.chatroomdemo.model.firebaseChain

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.techapp.james.chatroomdemo.model.convertBitmap.ConvertBitmap
import com.techapp.james.chatroomdemo.model.firebaseChain.firebaseDBObject.UserFirebaseObject
import com.techapp.james.chatroomdemo.model.userModel.User
import com.techapp.james.firebasechainofresponsibility.model.firebaseChain.FirebaseManager

/**
 * Created by James on 2018/4/17.
 */
class GetUserHandle : FirebaseManager {
    constructor()

    override fun handle(any: Any?) {
        //any is String(Login UID)

        var uid = any as String
        val myRef = database.getReference("User/" + uid)
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                var userFirebaseObject = dataSnapshot.getValue(UserFirebaseObject::class.java)
                var user = User()
                if (userFirebaseObject != null) {
                    if (!userFirebaseObject!!.id.equals("")) {
                        user.myId = userFirebaseObject!!.id
                        user.myName = userFirebaseObject.name
                        user.notDisplay = userFirebaseObject.notDisplay
                        user.email = userFirebaseObject.email
                        user.setIcon(ConvertBitmap.StringToBitMap(userFirebaseObject.icon)!!)
                    }
                }
                Log.d("GetUerHandle", user.myId)
                Log.d("GetUerHandle", user.myName)
                dbCallBack.dbCallBack(user)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.d("TAG", "Failed to read value.", error.toException())
            }
        })
    }
}