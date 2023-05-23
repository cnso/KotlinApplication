package org.jash.bindingapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.jash.bindingapplication.adapter.CommonAdapter
import org.jash.bindingapplication.adapter.MyBannerAdapter
import org.jash.bindingapplication.databinding.ActivityMain2Binding
import org.jash.bindingapplication.model.Category
import org.jash.bindingapplication.model.Product

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding =
            DataBindingUtil.setContentView<ActivityMain2Binding>(this, R.layout.activity_main2)
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
               return when(position) {
                    0, 1 -> 2
                    else -> 1
               }
            }
        }
        binding.productList.layoutManager = gridLayoutManager
        val myBannerAdapter = MyBannerAdapter()
        var subcategoryAdapter = CommonAdapter(mapOf(Category::class.java to (R.layout.category_item to BR.category)))

        var adapter = CommonAdapter(
            mapOf(
                Product::class.java to (R.layout.list_item to BR.product),
                CommonAdapter::class.java to (R.layout.navigation_item to BR.adapter),
                MyBannerAdapter::class.java to (R.layout.banner_item to BR.adapter)
            ),
            mutableListOf(
                myBannerAdapter,
                subcategoryAdapter
            )
        )
        var categoryAdapter = CommonAdapter(mapOf(Category::class.java to (R.layout.text_item to BR.category)))
        binding.adapter = adapter
        binding.categoryAdapter = categoryAdapter
        var create = retrofit.create(GoodService::class.java)
        val safeSubscribe =  SafeSubscribe(
            proscessor.ofType(String::class.java)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    when(it){
                        "clearProduct" -> adapter.removeIf { it.javaClass == Product::class.java}
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
                .subscribe { adapter += it.data },
            create.getBanner()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    logd(it.data.toString())
                    myBannerAdapter += it.data
                }
        )
        lifecycle.addObserver(safeSubscribe)
    }
}