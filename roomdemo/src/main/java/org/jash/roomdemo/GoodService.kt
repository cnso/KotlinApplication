package org.jash.roomdemo

import io.reactivex.rxjava3.core.Observable
import org.jash.roomdemo.model.Category
import org.jash.roomdemo.model.Res
import retrofit2.http.GET
import retrofit2.http.Query

interface GoodService {
    @GET("/goods/category")
    fun getCategory(@Query("parent_id") id:Int):Observable<Res<Category>>
}