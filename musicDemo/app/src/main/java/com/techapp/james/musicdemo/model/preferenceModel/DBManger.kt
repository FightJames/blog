package com.techapp.james.musicdemo.model.preferenceModel

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.techapp.james.musicdemo.model.musicModel.PlayLists

/**
 * Created by James on 2018/3/30.
 */
class DBManger {
    private var sharedPererences: SharedPreferences? = null
    private var data = "songLists"

    private constructor() {}

    companion object {
        val instance = DBManger()
            get() = field
    }

    public fun saveSongLists(input: Any, context: Context) {
        //Returns the SharedPreferences where this Preference can read its value(s).
        sharedPererences = context.getSharedPreferences(data, 0)
        var preEditor = sharedPererences!!.edit()
        var gson = Gson()
        var jsonString = gson.toJson(input)
        preEditor.putString(data, jsonString)
        preEditor.commit()
    }

    public fun getSongLists(context: Context): PlayLists? {
        sharedPererences = context.getSharedPreferences(data, 0)
        var gson = Gson()
        var json = sharedPererences!!.getString(data, "")
        println("Json "+ json)
        var songLists:PlayLists?=null
        if(!json.equals("{}")) {
             songLists = gson.fromJson(json, PlayLists::class.java)
        }
            return songLists
    }

}