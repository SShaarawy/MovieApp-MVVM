package com.example.movieapp.db

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun toString (list: List<Int>): String {
        return list.toString()
    }

    @TypeConverter
    fun toList(string: String): List<Int> {
        return string.removeSurrounding("[","]")
            .split(", ")
            .map{it.toInt()}
    }
}