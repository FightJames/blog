package com.techapp.james.stickyrecyclerview

interface Data {
    fun insertOrUpdate()
    fun getItem(_pos: Int)
    fun getTitles(): ArrayList<String>
    fun getTitle(_pos: Int): String
    fun count(): Int
    fun isTitle(title: String): Boolean
}