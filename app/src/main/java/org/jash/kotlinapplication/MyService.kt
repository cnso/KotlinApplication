package org.jash.kotlinapplication

import androidx.lifecycle.LifecycleService

class MyService: LifecycleService() {
    override fun onCreate() {
        super.onCreate()
        lifecycle.addObserver(MyData())
    }
}