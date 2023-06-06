package org.jash.roomdemo.repository

import io.reactivex.rxjava3.schedulers.Schedulers
import org.jash.common.processor
import org.jash.roomdemo.GoodService
import org.jash.roomdemo.database.AppDatabase
import retrofit2.Retrofit

class ApiRepository(val database: AppDatabase, val retrofit: Retrofit) {
    fun loadCategory(id:Int, loadLocal:Boolean = false, loadRemote:Boolean = true) {
        if (loadLocal) {
             val s = database.getCategoryDao().getCategory(id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe { it.forEach(processor::onNext) }
        }
        if (loadRemote) {
            val s = retrofit.create(GoodService::class.java).getCategory(id)
                .observeOn(Schedulers.io())
                .subscribe({
                    if (it.code == 200) {
                        if (loadLocal) {
                            processor.onNext("clearCategory")
                        }
                        it.data.forEach(processor::onNext)
                    } else {
                        processor.onNext(it.message)
                    }
                },{
                    processor.onNext(it.message ?: "网络出错" )
                })
        }
    }
}