package com.techapp.james.chatroomdemo.model.firebaseChain

import com.techapp.james.chatroomdemo.model.convertBitmap.ConvertBitmap
import com.techapp.james.chatroomdemo.model.firebaseChain.firebaseDBObject.UserFirebaseObject
import com.techapp.james.chatroomdemo.model.userModel.User
import com.techapp.james.firebasechainofresponsibility.model.firebaseChain.FirebaseManager

/**
 * Created by James on 2018/4/17.
 */
class SaveUserHandle : FirebaseManager {
    constructor()

    override fun handle(any: Any?) {
        var user = any as User
        //convert dbUser to firebase dbUser object
        var userFirebaseObject = UserFirebaseObject()
        userFirebaseObject.id = user.myId
        userFirebaseObject.name = user.myName
        userFirebaseObject.notDisplay = user.notDisplay
        userFirebaseObject.icon = ConvertBitmap.BitMapToString(user.getIcon()!!)
        userFirebaseObject.email = user.email
        val myRef = database.getReference("User/" + userFirebaseObject.id)
        myRef.setValue(userFirebaseObject)
    }
}