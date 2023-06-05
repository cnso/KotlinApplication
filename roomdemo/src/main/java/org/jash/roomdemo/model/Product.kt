package org.jash.roomdemo.model

import android.content.Context
import android.content.Intent
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.annotations.SerializedName
import org.jash.roomdemo.BR
import org.jash.roomdemo.DetailActivity

class Product(
    @SerializedName("bannerList")
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
            productList?.notifyPropertyChanged(BR.all)
            productList?.notifyPropertyChanged(BR.sum)
        }
    var productList: ProductList? = null
    fun showDetail(context: Context) {
        Intent(context, DetailActivity::class.java).also {
            it.putExtra("product_id", id)
            context.startActivity(it)
        }

    }

    override fun toString(): String {
        return "Product(bannerList=$bannerList, categoryId=$categoryId, goodsAttribute='$goodsAttribute', goodsBanner='$goodsBanner', goodsCode='$goodsCode', goodsDefaultIcon='$goodsDefaultIcon', goodsDefaultPrice=$goodsDefaultPrice, goodsDesc='$goodsDesc', goodsDetailOne='$goodsDetailOne', goodsDetailTwo='$goodsDetailTwo', goodsSalesCount=$goodsSalesCount, goodsStockCount=$goodsStockCount, id=$id, productList=$productList)"
    }

}