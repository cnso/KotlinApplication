package org.jash.roomdemo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.jash.roomdemo.dao.CategoryDao
import org.jash.roomdemo.dao.UserDao
import org.jash.roomdemo.model.Category
import org.jash.roomdemo.model.User

@Database(entities = [User::class, Category::class], version = 1)
@TypeConverters(MyTypeConverters::class)
abstract class AppDatabase:RoomDatabase() {
    abstract fun getUserDao():UserDao
    abstract fun getCategoryDao(): CategoryDao
}