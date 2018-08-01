package com.techapp.james.stickyrecyclerview.dataStructure

import java.util.*
import kotlin.collections.ArrayList

class ConcreteData : Data {
    val data: TreeMap<String, TreeSet<String>>

    constructor() {
        data = TreeMap<String, TreeSet<String>>()
    }

    override fun insertOrUpdate(title: String, item: String) {
        if (data.containsKey(title)) {
            var itemSet = data.get(title)
            itemSet!!.add(item)
        } else {
            var itemSet = TreeSet<String>()
            itemSet.add(item)
            data.put(title, itemSet)
        }
    }

    override fun remove(_pos: Int): Boolean {
        var pos = _pos
        for (key: String in data.keys) {
            var items = data.get(key)!!
            var res = find(key, items, pos)
            if (res != null) {
                if (pos == 0) {
                    data.remove(res)
                    return true
                } else {
                    data.get(key)!!.remove(res)
                    return true
                }
            }
            pos -= items.size + 1
        }
        return false
    }

    override fun getItem(_pos: Int): String {
        var pos = _pos
        for (key in data.keys) {
            var itemSet = data.get(key)!!
            var item = find(key, itemSet, pos)
            if (item != null) {
                return item
            }
            pos -= itemSet.size + 1
        }
        throw RuntimeException("Can't find concreteData")
    }

    override fun getTitles(): ArrayList<String> {
        var list = ArrayList<String>()
        data.forEach { k, v -> list.add(k) }
        return list
    }

    override fun getTitle(_pos: Int): String {
        var pos = _pos
        for (key: String in data.keys) {
            var itemSet = data.get(key)!!
            var item = find(key, itemSet, pos)
            if (item != null) {
                return key
            }
            pos -= itemSet.size + 1
        }
        throw RuntimeException("Can't find concreteData")
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

    override fun count(): Int {
        var count = 0
        data.forEach { t, u -> count += u.size + 1 }
        return count
    }

    override fun isTitle(title: String): Boolean {
        return data.containsKey(title)
    }
}