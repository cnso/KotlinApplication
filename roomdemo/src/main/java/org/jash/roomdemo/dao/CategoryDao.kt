package org.jash.roomdemo.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import io.reactivex.rxjava3.core.Maybe
import org.jash.roomdemo.model.Category

@Dao
interface CategoryDao {
    @Query("select * from category where parent_id=:id")
    fun getCategory(id:Int):Maybe<List<Category>>
    @Upsert
    fun insert(vararg category: Category)
}