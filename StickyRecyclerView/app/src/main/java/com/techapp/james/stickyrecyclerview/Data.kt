package com.techapp.james.stickyrecyclerview

import java.util.*
import kotlin.collections.ArrayList

class Data {
    val data: TreeMap<String, TreeSet<String>>

    constructor() {
        data = TreeMap<String, TreeSet<String>>()
    }

    fun insertOrUpdate(title: String, item: String) {
        if (data.containsKey(title)) {
            var itemSet = data.get(title)
            itemSet!!.add(item)
        } else {
            var itemSet = TreeSet<String>()
            itemSet.add(item)
            data.put(title, itemSet)
        }
    }

    fun getItem(_pos: Int): String {
        var pos = _pos
        for (key in data.keys) {
            var itemSet = data.get(key)!!
            var item = find(key, itemSet, pos)
            if (item != null) {
                return item
            }
            pos -= itemSet.size + 1
        }
        throw RuntimeException("Can't find data")
    }

    fun getTitles(): ArrayList<String> {
        var list = ArrayList<String>()
        data.forEach { k, v -> list.add(k) }
        return list
    }

    fun getTitle(_pos: Int): String {
        var pos = _pos
        for (key: String in data.keys) {
            var itemSet = data.get(key)!!
            var item = find(key, itemSet, pos)
            if (item != null) {
                return key
            }
            pos -= itemSet.size + 1
        }
        throw RuntimeException("Can't find data")
    }

    private fun find(key: String, items: TreeSet<String>, _pos: Int): String? {
        var pos = _pos
        if (pos == 0) {
            return key
        }
        pos--
        for (item: String in items) {
            if (pos == 0) {
                return item
            }
            pos--
        }
        return null
    }

    fun count(): Int {
        var count = 0
        data.forEach { t, u -> count += u.size + 1 }
        return count
    }

    fun isTitle(title: String): Boolean {
        return data.containsKey(title)
    }
}