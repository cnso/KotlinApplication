package org.jash.bindingapplication

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField

class User(var username:ObservableField<String>,  password:String):BaseObservable(){
    @get:Bindable
    var password = password
        set(value) {
            field = value
            notifyPropertyChanged(BR.password)
        }
    fun login() {
        Log.d(TAG, "login: " + this.toString())
    }
}
