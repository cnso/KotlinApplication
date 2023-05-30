package org.jash.bindingapplication

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.jash.bindingapplication.adapter.CommonAdapter
import org.jash.bindingapplication.adapter.MyBannerAdapter
import org.jash.bindingapplication.annotations.BindingLayout
import org.jash.bindingapplication.annotations.Subscribe
import org.jash.bindingapplication.databinding.ActivityMain2Binding
import org.jash.bindingapplication.model.BannerEntry
import org.jash.bindingapplication.model.Category
import org.jash.bindingapplication.model.Product


class MainActivity2 : BaseActivity() {

    @set:BindingLayout("activity_main2")
    lateinit var binding:ActivityMain2Binding
    lateinit var adapter: CommonAdapter<Any>
    lateinit var subcategoryAdapter:CommonAdapter<Category>
    lateinit var categoryAdapter:CommonAdapter<Category>
    lateinit var myBannerAdapter: MyBannerAdapter
    lateinit var mDrawerToggle:ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        var binding =
//            DataBindingUtil.setContentView<ActivityMain2Binding>(this, R.layout.activity_main2)
        setSupportActionBar(binding.toolbar)
        // These lines are needed to display the top-left hamburger button
        // These lines are needed to display the top-left hamburger button
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Make the hamburger button work

        // Make the hamburger button work
        mDrawerToggle = ActionBarDrawerToggle(this, binding.drawer, R.string.app_name, R.string.app_name)
        binding.drawer.addDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position) {
                    0, 1 -> 2
                    else -> 1
                }
            }
        }
        binding.productList.layoutManager = gridLayoutManager
        myBannerAdapter = MyBannerAdapter()
        subcategoryAdapter =
            CommonAdapter(mapOf(Category::class.java to (R.layout.category_item to BR.category)))

        adapter = CommonAdapter(
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
        categoryAdapter =
            CommonAdapter(mapOf(Category::class.java to (R.layout.text_item to BR.category)))
        binding.adapter = adapter
        binding.categoryAdapter = categoryAdapter
        var create = retrofit.create(GoodService::class.java)
        val safeSubscribe = SafeSubscribe(
//            proscessor.ofType(String::class.java)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//                    when(it){
//                        "clearProduct" -> adapter.removeIf { it.javaClass == Product::class.java}
//                        "clearSubcategory" -> subcategoryAdapter.clear()
//                        else -> Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
//                    }
//                },
//            proscessor.ofType(Product::class.java)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {adapter += it},
//            proscessor.ofType(Category::class.java)
//                .filter {it.parent_id != 0}
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {subcategoryAdapter += it},
            create.getCategory(0)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it.data.forEach(proscessor::onNext) },
            create.getCategory(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it.data.forEach(proscessor::onNext) },
            create.getProducts(0, 1, 10)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it.data.forEach(proscessor::onNext) },
            create.getBanner()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it.data.forEach(proscessor::onNext) }
        )
        lifecycle.addObserver(safeSubscribe)
    }

    override fun onOptionsItemSelected(item: MenuItem) = mDrawerToggle.onOptionsItemSelected(item) && super.onOptionsItemSelected(item)

    @Subscribe
    fun subscribeProduct(product: Product) {
        adapter += product
    }

    @Subscribe
    fun subscribeMessage(s: String) {
        when (s) {
            "clearProduct" -> adapter.removeIf { it.javaClass == Product::class.java }
            "clearSubcategory" -> subcategoryAdapter.clear()
            else -> Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
        }
    }
    @Subscribe(filter = ["isSubcategory"])
    fun subscribeSubcategory(category: Category) {
        logd("subscribeSubcategory $category")
        subcategoryAdapter += category
    }
    fun isSubcategory(category: Category) = category.parent_id != 0
    @Subscribe(filter = ["isMaincategory"])
    fun displayMaincategory(category: Category) {
        categoryAdapter += category
    }
    fun isMaincategory(category: Category) = category.parent_id == 0
    @Subscribe
    fun displayBanner(bannerEntry: BannerEntry) {
        myBannerAdapter += bannerEntry
    }
}