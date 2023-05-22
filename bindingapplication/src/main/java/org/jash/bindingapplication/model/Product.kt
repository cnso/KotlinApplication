package org.jash.bindingapplication.model

data class Product(
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
)