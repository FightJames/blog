package com.techapp.james.chatroomdemo.model.userModel

import com.google.firebase.auth.FirebaseUser

/**
 * Created by James on 2018/4/17.
 */
object CurrentUser {
    var dbUser: User? = null
    var fireBaseUser: FirebaseUser?=null
}