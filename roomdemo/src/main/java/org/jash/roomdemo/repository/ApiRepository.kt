package org.jash.roomdemo.repository

import io.reactivex.rxjava3.schedulers.Schedulers
import org.jash.roomdemo.GoodService
import org.jash.roomdemo.database.AppDatabase
import org.jash.roomdemo.proscessor
import retrofit2.Retrofit

class ApiRepository(val database: AppDatabase, val retrofit: Retrofit) {
    fun loadCategory(id:Int, loadLocal:Boolean = false, loadRemote:Boolean = true) {
        if (loadLocal) {
             val s = database.getCategoryDao().getCategory(id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe { it.forEach(proscessor::onNext) }
        }
        if (loadRemote) {
            val s = retrofit.create(GoodService::class.java).getCategory(id)
                .observeOn(Schedulers.io())
                .subscribe({
                    if (it.code == 200) {
                        if (loadLocal) {
                            proscessor.onNext("clearCategory")
                        }
                        it.data.forEach(proscessor::onNext)
                    } else {
                        proscessor.onNext(it.message)
                    }
                },{
                    proscessor.onNext(it.message ?: "网络出错" )
                })
        }
    }
}