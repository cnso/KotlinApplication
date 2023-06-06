package org.jash.common.activity

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jash.common.annotations.OnThread
import org.jash.common.annotations.Subscribe
import org.jash.common.processor
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotations
import kotlin.reflect.full.functions

class SafeSubscribe(vararg val d: Disposable) : LifecycleEventObserver {
    constructor(owner: LifecycleOwner) : this(*owner.javaClass.kotlin.let { clazz ->
        clazz.functions.filter {
            it.findAnnotations(Subscribe::class).isNotEmpty()
        }
            .map {
                processor.ofType((it.parameters[1].type.classifier as KClass<*>).java)
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
                                    clazz.functions.find { f -> f.name == name }?.call(owner, data)
                                }.filterIsInstance<Boolean>().reduce { b1, b2 -> b1 && b2 }
                            }
                    }
                    .subscribe { data -> it.call(owner, data) }
            }.toTypedArray()
    }) {
        owner.lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_STOP -> d.filter { !it.isDisposed }.forEach { it.dispose() }
            else -> {}
        }
    }

}