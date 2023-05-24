package org.jash.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jash.roomdemo.databinding.ActivityMain2Binding
import org.jash.roomdemo.model.Category

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMain2Binding>(this, R.layout.activity_main2)
        val adapter = CommonAdapter<Category>(mapOf(Category::class.java to (R.layout.item to BR.category)))
        binding.adapter = adapter
        proscessor.ofType(String::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when(it) {
                    "clearCategory" -> adapter.clear()
                    else -> Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                }
            }
        proscessor.ofType(Category::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { adapter += it }
        retrofit.create(GoodService::class.java).getCategory(1)
            .subscribe {
                proscessor.onNext("clearCategory")
                it.data.forEach(proscessor::onNext)
            }
        database.getCategoryDao().getCategory(1)
            .subscribeOn(Schedulers.io())
            .subscribe {
                logd(it.toString())
                it.forEach(proscessor::onNext)
            }

    }
}