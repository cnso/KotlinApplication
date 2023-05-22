package org.jash.bindingapplication.model

import org.jash.bindingapplication.GoodService
import org.jash.bindingapplication.proscessor
import org.jash.bindingapplication.retrofit

data class Category(
    val category_icon: String,
    val category_name: String,
    val id: Int,
    val parent_id: Int
) {
    fun loadProduct() {
        proscessor.onNext("clearProduct")
        retrofit.create(GoodService::class.java)
            .getProducts(id, 1, 10)
            .subscribe {
                if (it.data.isEmpty()) {
                    proscessor.onNext("此分类为空")
                } else {
                    it.data.forEach(proscessor::onNext)
                }
            }
    }
}