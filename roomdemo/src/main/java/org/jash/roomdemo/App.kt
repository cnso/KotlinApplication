package org.jash.roomdemo

import android.app.Application
import androidx.room.Room
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jash.roomdemo.database.AppDatabase
import org.jash.roomdemo.model.Category

class App:Application() {
    lateinit var database: AppDatabase
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, AppDatabase::class.java, "demo").build()
        proscessor.ofType(Category::class.java).observeOn(Schedulers.io())
            .subscribe { database.getCategoryDao().insert(it)}
    }
}