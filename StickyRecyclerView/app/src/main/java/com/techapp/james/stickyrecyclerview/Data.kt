package com.techapp.james.stickyrecyclerview

interface Data {
    fun insertOrUpdate(title:String,item:String)
    fun getItem(_pos: Int):String
    fun getTitles(): ArrayList<String>
    fun getTitle(_pos: Int): String
    fun count(): Int
    fun isTitle(title: String): Boolean
}