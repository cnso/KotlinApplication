package org.jash.roomdemo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import org.jash.common.activity.BaseActivity
import org.jash.common.adapter.CommonAdapter
import org.jash.common.annotations.Subscribe
import org.jash.common.processor
import org.jash.roomdemo.databinding.ActivityMain2Binding
import org.jash.roomdemo.model.Product
import org.jash.roomdemo.model.ProductList

class MainActivity2 : BaseActivity() {
    lateinit var adapter: CommonAdapter<Product>
    lateinit var binding:ActivityMain2Binding
    lateinit var productList:ProductList
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main2)
        setSupportActionBar(binding.toolbar)
//        val main2ViewModel by viewModels<Main2ViewModel> ()
        productList = ProductList()
        adapter =
            CommonAdapter(mapOf(Product::class.java to (R.layout.item to BR.product)), productList.data)
        binding.adapter = adapter
        binding.list = productList
//        val safeSubscribe = SafeSubscribe(
//            proscessor.ofType(String::class.java)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//                    when (it) {
//                        "clearCategory" -> adapter.clear()
//                        else -> Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
//                    }
//                },
//            proscessor.ofType(Category::class.java)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//                    binding.refresh.isRefreshing = false
//                    adapter += it
//                           },
//
//        )
//        val apiRepository = ApiRepository(database, retrofit)
//        if (main2ViewModel.category.isEmpty()) {
//            apiRepository.loadCategory(1, loadLocal = true)
////            retrofit.create(GoodService::class.java).getCategory(1)
////                .subscribe {
////                    logd("网络请求")
////                    proscessor.onNext("clearCategory")
////                    it.data.forEach(proscessor::onNext)
////                }
////            database.getCategoryDao().getCategory(1)
////                .subscribeOn(Schedulers.io())
////                .subscribe {
////                    logd(it.toString())
////                    it.forEach(proscessor::onNext)
////                }
//        }
//        binding.refresh.setColorSchemeColors(0xff0000, 0x00ffff, 0x00ff00)
//        binding.refresh.setOnRefreshListener {
//            adapter.clear()
//            apiRepository.loadCategory(1)
//        }
        retrofit.create(GoodService::class.java).getProducts(0, 1, 5)
            .subscribe { it.data.forEach(processor::onNext) }

    }
    @Subscribe
    fun loadMessage(s:String) {
        when (s) {
            "clearCategory" -> adapter.clear()
            else -> Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
        }
    }
    @Subscribe
    fun displayProduct(category: Product) {
        binding.refresh.isRefreshing = false
        category.productList = productList
        adapter += category
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_cart -> {
                startActivity(Intent(this, MainActivity3::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}