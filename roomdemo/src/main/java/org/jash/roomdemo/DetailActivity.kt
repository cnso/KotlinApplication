package org.jash.roomdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.google.gson.GsonBuilder
import org.jash.common.activity.BaseActivity
import org.jash.common.annotations.BindingLayout
import org.jash.common.annotations.Subscribe
import org.jash.common.proscessor
import org.jash.roomdemo.adapter.ProductBannerAdapter
import org.jash.roomdemo.databinding.ActivityDetailBinding
import org.jash.roomdemo.databinding.ConfigItemBinding
import org.jash.roomdemo.model.Product

class DetailActivity : BaseActivity() {
    @set:BindingLayout("activity_detail")
    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val goodService = retrofit.create(GoodService::class.java)
        val id = intent.getIntExtra("product_id", 0)
        setSupportActionBar(binding.toolbar)
        goodService.getDetail(id).subscribe{ proscessor.onNext(it.data)}
    }

    @Subscribe
    fun showProduct(product: Product) {
        binding.product = product
        binding.banner.setAdapter(ProductBannerAdapter(product.bannerList))
        val gson = GsonBuilder().create()
        val map =
            gson.fromJson<Map<String, List<String>>>(product.goodsAttribute, Map::class.java)
        map.entries.forEach {
            val itemBinding = DataBindingUtil.inflate<ConfigItemBinding>(
                LayoutInflater.from(this),
                R.layout.config_item,
                binding.config,
                false
            )
            itemBinding.textInput.hint = it.key
            itemBinding.spinner.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, it.value))
            binding.config.addView(itemBinding.root)
        }

    }
}