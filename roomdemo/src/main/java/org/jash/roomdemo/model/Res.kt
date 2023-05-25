package org.jash.roomdemo.model

data class Res<T>(
    var code:Int,
    var message:String,
    var data:T
)
