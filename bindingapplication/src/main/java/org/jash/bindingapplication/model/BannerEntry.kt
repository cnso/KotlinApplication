package org.jash.bindingapplication.model

import com.google.gson.annotations.SerializedName

data class BannerEntry(
    val desc: String,
    val id: Int,
    @SerializedName("imagePath")
    val imagePath: String,
    @SerializedName("isVisible")
    val isVisible: Int,
    val order: Int
)