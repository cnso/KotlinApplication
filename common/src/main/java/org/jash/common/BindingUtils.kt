package org.jash.common

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

@BindingAdapter("android:image_url")
fun setImageUrl(view: ImageView, url:String?) {
    Glide.with(view).load(url).into(view)
}
@BindingAdapter("android:image_url_ww")
fun setImageUrlWithWidth(view: ImageView, url:String?) {
    Glide.with(view).load(url)
        .listener(object :RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean = false

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                val layoutParams = view.layoutParams
                val width = view.width
                val height = resource?.let { width * (it.minimumHeight.toFloat() / it.minimumWidth ) }?.toInt() ?:0
                layoutParams.width = width
                layoutParams.height = height
                view.layoutParams = layoutParams
                logd("onResourceReady: $width, $height")
                return false
            }
        })
        .into(view)
}
