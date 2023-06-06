package org.jash.common

import android.util.Log
import io.reactivex.rxjava3.processors.PublishProcessor

val Any.TAG
    get() = this.javaClass.simpleName
fun Any.logd(s:String) = Log.d(TAG, s)

val processor by lazy {
    PublishProcessor.create<Any>()
}
