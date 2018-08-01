package com.techapp.james.stickyrecyclerview.dataStructure

interface Data {
    fun insertOrUpdate(title: String, item: String)
    fun remove(_pos: Int): Boolean
    fun getItem(_pos: Int): String
    fun getTitles(): ArrayList<String>
    fun getTitle(_pos: Int): String
    fun count(): Int
    fun isTitle(title: String): Boolean
}