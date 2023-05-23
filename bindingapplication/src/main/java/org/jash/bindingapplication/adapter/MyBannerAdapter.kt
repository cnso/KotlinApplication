package org.jash.bindingapplication.adapter

import com.bumptech.glide.Glide
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import org.jash.bindingapplication.model.Banner

class MyBannerAdapter(data:MutableList<Banner> = mutableListOf()):BannerImageAdapter<Banner>(data) {
    override fun onBindView(holder: BannerImageHolder?, data: Banner?, position: Int, size: Int) {
        Glide.with(holder!!.imageView).load(data?.imagePath).into(holder.imageView)
    }
    operator fun plusAssign(banner: Banner) {
        mDatas.add(banner)
        notifyItemInserted(mDatas.size)
    }
    operator fun plusAssign(collection: Collection<Banner>) {
        val size = mDatas.size
        mDatas.addAll(collection)
        notifyItemRangeInserted(size, collection.size)
    }
}