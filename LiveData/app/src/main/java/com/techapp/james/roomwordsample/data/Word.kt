package com.techapp.james.roomwordsample.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "word_table")
class Word {
    @PrimaryKey
    @ColumnInfo(name = "word")
    var mWord: String = ""
        private set(value) {
            field = value
        }
        public get() = field

    constructor(word: String) {
        this.mWord = word
    }
}