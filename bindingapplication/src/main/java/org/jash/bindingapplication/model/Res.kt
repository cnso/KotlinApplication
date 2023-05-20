package org.jash.bindingapplication.model

data class Res<D>(
    var code:Int,
    var message:String,
    var data:MutableList<D>
)
