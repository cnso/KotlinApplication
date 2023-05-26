package org.jash.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jash.roomdemo.databinding.ActivityMain2Binding
import org.jash.roomdemo.model.Category
import org.jash.roomdemo.viewmodel.Main2ViewModel

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMain2Binding>(this, R.layout.activity_main2)
        val main2ViewModel by viewModels<Main2ViewModel> ()
        val adapter =
            CommonAdapter<Category>(mapOf(Category::class.java to (R.layout.item to BR.category)), main2ViewModel.category)
        binding.adapter = adapter
        val safeSubscribe = SafeSubscribe(
            proscessor.ofType(String::class.java)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    when (it) {
                        "clearCategory" -> adapter.clear()
                        else -> Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                    }
                },
            proscessor.ofType(Category::class.java)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { adapter += it },
        )
        if (main2ViewModel.category.isEmpty()) {
            retrofit.create(GoodService::class.java).getCategory(1)
                .subscribe {
                    logd("网络请求")
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
}