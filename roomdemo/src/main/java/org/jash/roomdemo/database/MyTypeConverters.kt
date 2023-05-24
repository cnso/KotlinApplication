package org.jash.roomdemo.database

import androidx.room.TypeConverter

class MyTypeConverters {
    @TypeConverter
    fun toNames(src:String):List<String> = src.split(",")
    @TypeConverter
    fun fromNames(src: List<String>): String = src.joinToString(",")
}