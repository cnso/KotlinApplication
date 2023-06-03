package org.jash.roomdemo.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import org.jash.roomdemo.BR

class Product(
    val bannerList: List<String>,
    val categoryId: Int,
    val goodsAttribute: String,
    val goodsBanner: String,
    val goodsCode: String,
    val goodsDefaultIcon: String,
    val goodsDefaultPrice: Double,
    val goodsDesc: String,
    val goodsDetailOne: String,
    val goodsDetailTwo: String,
    val goodsSalesCount: Int,
    val goodsStockCount: Int,
    val id: Int
) : BaseObservable() {
    @get:Bindable
    var checked: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.checked)
            productList.notifyPropertyChanged(BR.all)
            productList.notifyPropertyChanged(BR.sum)
        }
    lateinit var productList: ProductList
}