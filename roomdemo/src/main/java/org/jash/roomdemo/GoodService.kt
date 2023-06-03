package org.jash.roomdemo

import io.reactivex.rxjava3.core.Observable
import org.jash.roomdemo.model.Category
import org.jash.roomdemo.model.Product
import org.jash.roomdemo.model.Res
import org.jash.roomdemo.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GoodService {
    @GET("/goods/category")
    fun getCategory(@Query("parent_id") id:Int):Observable<Res<List<Category>>>
    @POST("/user/loginjson")
    fun login(@Body user:User):Observable<Res<User>>
    @GET("/goods/info")
    fun getProducts(
        @Query("category_id") id:Int,
        @Query("currentPage") currentPage:Int,
        @Query("pageSize") pageSize:Int
    ):Observable<Res<List<Product>>>
}