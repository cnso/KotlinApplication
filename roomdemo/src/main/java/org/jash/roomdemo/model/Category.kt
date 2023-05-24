package org.jash.roomdemo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    val category_icon: String,
    val category_name: String,
    @PrimaryKey
    val id: Int,
    val parent_id: Int
)