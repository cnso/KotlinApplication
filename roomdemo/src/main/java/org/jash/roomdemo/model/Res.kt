package org.jash.roomdemo.model

data class Res<D>(
    var code:Int,
    var message:String,
    var data:MutableList<D>
)
