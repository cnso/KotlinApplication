package org.jash.roomdemo

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.jash.roomdemo.model.User
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
var loginUser:User? = null
private val client = OkHttpClient.Builder()
    .addInterceptor{
        val builder = it.request().newBuilder()
        loginUser?.token?.let {token ->
            builder.header("token", token)
        }
        it.proceed(builder.build())
    }
    .build()
val retrofit: Retrofit by lazy {
    Retrofit.Builder()
        .client(client)
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
val Context.database
    get() = (this.applicationContext as App).database