package org.jash.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jash.roomdemo.database.AppDatabase
import org.jash.roomdemo.databinding.ActivityMainBinding
import org.jash.roomdemo.model.Save
import org.jash.roomdemo.model.User

class MainActivity : AppCompatActivity() {
    val TAG = this.javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val database =
            Room.databaseBuilder(applicationContext, AppDatabase::class.java, "demo")
                .build()
        val userDao = database.getUserDao()
        userDao.getUsers().subscribeOn(Schedulers.io()).subscribe {
            Log.d(TAG, "onCreate: $it ${Thread.currentThread().name}")
        }
        val user = User(username = "", password = "")
        val save = Save(userDao)
        binding.user = user
        binding.save = save
    }
}