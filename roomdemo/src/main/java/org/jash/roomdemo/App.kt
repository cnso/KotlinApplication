package org.jash.roomdemo

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.room.Room
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jash.common.activity.SafeSubscribe
import org.jash.common.proscessor
import org.jash.roomdemo.database.AppDatabase
import org.jash.roomdemo.model.Category
import org.jash.roomdemo.model.User

class App : Application() {
    lateinit var database: AppDatabase
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, AppDatabase::class.java, "demo").build()
        val safeSubscribe = SafeSubscribe(
            proscessor.ofType(Category::class.java).observeOn(Schedulers.io())
                .subscribe { database.getCategoryDao().insert(it) },
            proscessor.ofType(User::class.java).observeOn(Schedulers.io())
                .subscribe { database.getUserDao().insert(it) }
        )
//        ProcessLifecycleOwner.get().lifecycle.addObserver(safeSubscribe)

    }
}