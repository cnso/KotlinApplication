package org.jash.bindingapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.databinding.BaseObservable
import java.net.URL
import androidx.databinding.Bindable

class ImageBean(var url:String, bitmap: Bitmap) :BaseObservable(){
    @get:Bindable
    var bitmap:Bitmap = bitmap
        set(value) {
            field = value
            notifyPropertyChanged(BR.bitmap)
        }
    fun load() {
        Thread {
            bitmap = BitmapFactory.decodeStream(URL(url).openStream())
        }.start()
    }
}