package org.jash.roomdemo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
//   autoGenerate 只支持 Int 和 Long
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,
    @ColumnInfo
    var username:String,
    var password:String,
    var names:List<String> = listOf("123","321","456")
)
