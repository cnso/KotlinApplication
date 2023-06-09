package org.jash.bindingapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jash.bindingapplication.annotations.BindingLayout
import org.jash.bindingapplication.annotations.OnThread
import org.jash.bindingapplication.annotations.Subscribe
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.*

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val clazz = this.javaClass.kotlin
        val safeSubscribe = SafeSubscribe(*clazz.functions.filter {
            it.findAnnotations(Subscribe::class).isNotEmpty()
        }
            .map {
                proscessor.ofType((it.parameters[1].type.classifier as KClass<*>).java)
                    .observeOn(
                        when (it.findAnnotations(Subscribe::class)[0].onThread) {
                            OnThread.MAIN_THREAD -> AndroidSchedulers.mainThread()
                            OnThread.IO_THREAD -> Schedulers.io()
                        }
                    )
                    .let { flow ->
                        val filter = it.findAnnotations(Subscribe::class)[0].filter
                        if (filter.isEmpty()) flow else
                            flow.filter { data ->
                                filter.map { name ->
                                    clazz.functions.find { f -> f.name == name }?.call(this, data)
                                }.filterIsInstance<Boolean>().reduce { b1, b2 -> b1 && b2 }
                            }
                    }
                    .subscribe { data -> it.call(this, data) }
            }.toTypedArray()
        )
        lifecycle.addObserver(safeSubscribe)
//        R.layout::class.java.getField("activity_main2").get(null)?.let { logd(it.toString()) }
        val property = clazz.declaredMemberProperties.filterIsInstance<KMutableProperty1<BaseActivity, *>>()
            .find { it.setter.findAnnotations(BindingLayout::class).isNotEmpty() }
        val layoutId:Int = resources.getIdentifier(property?.let { it.setter.findAnnotations(BindingLayout::class)[0].resName }, "layout", packageName)
        property?.set(this, DataBindingUtil.setContentView(this, layoutId))
    }
}