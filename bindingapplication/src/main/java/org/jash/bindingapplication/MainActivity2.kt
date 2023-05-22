package org.jash.bindingapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.jash.bindingapplication.adapter.CommonAdapter
import org.jash.bindingapplication.databinding.ActivityMain2Binding
import org.jash.bindingapplication.model.Category
import org.jash.bindingapplication.model.Product

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding =
            DataBindingUtil.setContentView<ActivityMain2Binding>(this, R.layout.activity_main2)
        var adapter = CommonAdapter(mapOf(Product::class.java to (R.layout.list_item to BR.product)))
        var subcategoryAdapter = CommonAdapter(mapOf(Category::class.java to (R.layout.category_item to BR.category)))
        var categoryAdapter = CommonAdapter(mapOf(Category::class.java to (R.layout.text_item to BR.category)))
        binding.adapter = adapter
        binding.subcategoryAdapter = subcategoryAdapter
        binding.categoryAdapter = categoryAdapter
        var create = retrofit.create(GoodService::class.java)
        val safeSubscribe =  SafeSubscribe(
            proscessor.ofType(String::class.java)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    when(it){
                        "clearProduct" -> adapter.clear()
                        "clearSubcategory" -> subcategoryAdapter.clear()
                        else -> Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                    }
                },
            proscessor.ofType(Product::class.java)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {adapter += it},
            proscessor.ofType(Category::class.java)
                .filter {it.parent_id != 0}
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {subcategoryAdapter += it},
            create.getCategory(0)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    categoryAdapter += it.data
                           },

            create.getCategory(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { subcategoryAdapter += it.data },
            create.getProducts(0, 1, 10)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { adapter += it.data }
        )
        lifecycle.addObserver(safeSubscribe)
    }
}