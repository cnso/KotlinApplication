package org.jash.roomdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.jash.roomdemo.model.User

class UserViewModel:ViewModel() {
    var user = User()
    val liveData = MutableLiveData<String>()
}