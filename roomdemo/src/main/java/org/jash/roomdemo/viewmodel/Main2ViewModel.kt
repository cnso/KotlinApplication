package org.jash.roomdemo.viewmodel

import androidx.lifecycle.ViewModel
import org.jash.roomdemo.model.Category

class Main2ViewModel:ViewModel() {
    val category = mutableListOf<Category>()
}