package org.jash.common.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jash.common.annotations.BindingLayout
import org.jash.common.annotations.OnThread
import org.jash.common.annotations.Subscribe
import org.jash.common.processor
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.*

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val clazz = this.javaClass.kotlin
        SafeSubscribe(this)
        val property = clazz.declaredMemberProperties.filterIsInstance<KMutableProperty1<BaseActivity, *>>()
            .find { it.setter.findAnnotations(BindingLayout::class).isNotEmpty() }
        val layoutId:Int = property?.let { it.setter.findAnnotations(BindingLayout::class)[0].resName }
            ?.let { resources.getIdentifier(it, "layout", packageName) } ?: 0
        property?.set(this, DataBindingUtil.setContentView(this, layoutId))
    }
}