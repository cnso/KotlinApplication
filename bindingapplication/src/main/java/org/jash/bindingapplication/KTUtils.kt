package org.jash.bindingapplication

import android.util.Log
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.processors.PublishProcessor
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val Any.TAG
    get() = this.javaClass.simpleName
fun Any.logd(s:String) = Log.d(TAG, s)
val Any.retrofit by lazy {
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(
            GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
        ))
//            在 IO 线程中执行网络操作
        .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .baseUrl("http://43.143.146.165:8181/")
        .build()
}
val proscessor by lazy {
    PublishProcessor.create<Any>()
}