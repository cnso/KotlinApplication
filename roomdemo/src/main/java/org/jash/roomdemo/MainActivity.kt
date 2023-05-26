package org.jash.roomdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.room.Room
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jash.roomdemo.database.AppDatabase
import org.jash.roomdemo.databinding.ActivityMainBinding
import org.jash.roomdemo.model.Save
import org.jash.roomdemo.model.User

class MainActivity : AppCompatActivity() {
    val TAG = this.javaClass.simpleName
    private lateinit var safeSubscribe: SafeSubscribe
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val userDao = database.getUserDao()
        safeSubscribe = SafeSubscribe(
            proscessor.ofType(User::class.java)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val intent = Intent(this, MainActivity2::class.java)
                    startActivity(intent)
                    finish()
                },
            proscessor.ofType(String::class.java)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    when (it) {
                        else -> Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                    }
                },
            )
        lifecycle.addObserver(safeSubscribe)
        val user = User(username = "", password = "")
        binding.user = user
    }
}