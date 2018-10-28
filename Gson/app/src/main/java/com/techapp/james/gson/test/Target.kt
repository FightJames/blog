package com.techapp.james.gson.test

import android.util.Log
import com.google.gson.annotations.SerializedName

class Target {
    var status = ""
    var first_name = ""
    var last_name = 0
    var inS = InSidwe()

    constructor(status: String, first_name: String,inS:InSidwe) {
        this.status = status
        this.first_name = first_name
        this.inS=inS
        Log.d("Tag", "Pass1")
    }

    constructor(status: String, last_name: Int) {
        this.status = status
//        this.first_name = first_name
        this.last_name = last_name

        Log.d("Tag", "Pass")
    }
}