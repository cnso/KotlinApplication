package org.jash.bindingapplication.adapter

import com.bumptech.glide.Glide
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import org.jash.bindingapplication.model.BannerEntry

class MyBannerAdapter(data:MutableList<BannerEntry> = mutableListOf()):BannerImageAdapter<BannerEntry>(data) {
    override fun onBindView(holder: BannerImageHolder?, data: BannerEntry?, position: Int, size: Int) {
        holder?.imageView?.let { Glide.with(it).load(data?.imagePath).into(it) }
    }
    operator fun plusAssign(banner: BannerEntry) {
        mDatas.add(banner)
        notifyItemInserted(mDatas.size)
    }
    operator fun plusAssign(collection: Collection<BannerEntry>) {
        val size = mDatas.size
        mDatas.addAll(collection)
        notifyItemRangeInserted(size, collection.size)
    }
}