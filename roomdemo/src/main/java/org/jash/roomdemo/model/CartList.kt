package org.jash.roomdemo.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class CartList:BaseObservable(){
    val data = mutableListOf<Cart>()
    @get:Bindable
    var all:Boolean
        get() = data.map { it.checked }.let { if(it.isEmpty()) false else it.reduce(Boolean::and) }
        set(value) {
            data.forEach { it.checked = value }
        }
    @get:Bindable
    val sum:Double
        get() = data.filter { it.checked }.map { (it.goods_default_price ?: 0.toDouble()) * it.count }.let {if (it.isEmpty()) 0.toDouble() else it.reduce(Double::plus) }
    fun inverse() {
        data.forEach { it.checked = !it.checked }
    }
}