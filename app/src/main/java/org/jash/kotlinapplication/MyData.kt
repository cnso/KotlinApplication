package org.jash.kotlinapplication

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner

class MyData:LifecycleEventObserver {
//    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun load(){
        logd("加载数据中")
    }
//    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun release() {
        logd("释放资源")
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when(event) {
            Lifecycle.Event.ON_CREATE -> load()
            Lifecycle.Event.ON_STOP -> release()
            else -> {}
        }
    }
}