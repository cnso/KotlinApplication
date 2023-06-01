package org.jash.common.activity

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import io.reactivex.rxjava3.disposables.Disposable

class SafeSubscribe(vararg val d: Disposable):LifecycleEventObserver {
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when(event) {
            Lifecycle.Event.ON_PAUSE -> d.filter { !it.isDisposed }.forEach { it.dispose() }
            else -> {}
        }
    }

}