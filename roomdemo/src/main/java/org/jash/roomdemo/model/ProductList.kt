package org.jash.roomdemo.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class ProductList:BaseObservable(){
    val data = mutableListOf<Product>()
    @get:Bindable
    var all:Boolean
        get() = data.map { it.checked }.let { if(it.isEmpty()) false else it.reduce(Boolean::and) }
        set(value) {
            data.forEach { it.checked = value }
        }
    @get:Bindable
    val sum:Double
        get() = data.filter { it.checked }.map { it.goodsDefaultPrice }.let { if(it.isEmpty()) 0.toDouble() else it.reduce(Double::plus) }
}