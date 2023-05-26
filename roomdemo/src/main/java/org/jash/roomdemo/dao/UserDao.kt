package org.jash.roomdemo.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.jash.roomdemo.model.User

@Dao
interface UserDao {
//   Maybe 只查一次
    @Query("select * from user")
    fun getUsers():Maybe<List<User>>
//  Observable  有变化时,会自动查询
    @Query("select * from user where id = :id")
    fun getUserById(id:Int):Observable<User>
    @Upsert
    fun insert(vararg user: User)
    @Delete
    fun deleteById(user: User)
}