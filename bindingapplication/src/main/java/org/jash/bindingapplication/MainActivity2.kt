package org.jash.bindingapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.jash.bindingapplication.adapter.CommonAdapter
import org.jash.bindingapplication.databinding.ActivityMain2Binding
import org.jash.bindingapplication.model.Product

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding =
            DataBindingUtil.setContentView<ActivityMain2Binding>(this, R.layout.activity_main2)
        var adapter = CommonAdapter(mapOf(Product::class.java to (R.layout.list_item to BR.product)))
        binding.adapter = adapter
        retrofit.create(GoodService::class.java).getProducts(0, 1, 10)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { adapter += it.data }
    }
}