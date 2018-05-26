package com.techapp.james.firebasechainofresponsibility.model.firebaseChain

import com.google.firebase.database.FirebaseDatabase

/**
 * Created by James on 2018/4/17.
 */
abstract class FirebaseManager {
    protected val database = FirebaseDatabase.getInstance()
    var next: FirebaseManager?=null
    var dbCallBack: DBCallBack = object : DBCallBack { //set callback
        override fun dbCallBack(any: Any) {
        }
    }
    constructor(){

    }

    abstract fun handle(any: Any?)
}