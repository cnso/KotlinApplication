package org.jash.bindingapplication

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.youth.banner.Banner
import org.jash.bindingapplication.adapter.MyBannerAdapter

@BindingAdapter("android:image_bitmap")
fun setBitmap(view:ImageView, bitmap: Bitmap) {
    view.setImageBitmap(bitmap)
}
@BindingAdapter("android:image_url")
fun setImageUrl(view: ImageView, url:String?) {
    Glide.with(view).load(url).into(view)
}
@BindingAdapter("android:adapter")
fun setAdapter(banner: Banner<org.jash.bindingapplication.model.Banner, MyBannerAdapter>, myBannerAdapter: MyBannerAdapter) {
    if (banner.adapter != myBannerAdapter) {
        banner.setAdapter(myBannerAdapter)
    }
}