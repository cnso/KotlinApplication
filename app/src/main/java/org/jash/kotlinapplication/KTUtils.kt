package org.jash.kotlinapplication

import android.util.Log

val Any.TAG
    get() = this.javaClass.simpleName
fun Any.logd(s:String) = Log.d(TAG, s)