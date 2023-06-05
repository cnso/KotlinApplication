package org.jash.roomdemo.adapter

import com.bumptech.glide.Glide
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder

class ProductBannerAdapter(data:List<String>):BannerImageAdapter<String>(data) {
    override fun onBindView(holder: BannerImageHolder?, data: String?, position: Int, size: Int) {
        holder?.imageView?.let { Glide.with(it).load(data).into(it) }
    }
}