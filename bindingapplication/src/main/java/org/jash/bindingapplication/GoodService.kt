package org.jash.bindingapplication

import io.reactivex.rxjava3.core.Observable
import org.jash.bindingapplication.model.Category
import org.jash.bindingapplication.model.Product
import org.jash.bindingapplication.model.Res
import retrofit2.http.GET
import retrofit2.http.Query

interface GoodService {
    @GET("/goods/info")
    fun getProducts(
        @Query("category_id") id:Int,
        @Query("currentPage") currentPage:Int,
        @Query("pageSize") pageSize:Int
    ):Observable<Res<Product>>
    @GET("/goods/category")
    fun getCategory(@Query("parent_id") id:Int):Observable<Res<Category>>
}