package org.jash.roomdemo

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jash.common.activity.SafeSubscribe
import org.jash.common.proscessor
import org.jash.roomdemo.databinding.ActivityMainBinding
import org.jash.roomdemo.model.User
import org.jash.roomdemo.viewmodel.UserViewModel

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
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        binding.rawUsername.setAdapter(adapter)
        lifecycle.addObserver(safeSubscribe)
        val userViewModel by viewModels<UserViewModel>()
        val user = userViewModel.user
        binding.user = user

        database.getUserDao().getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { adapter.addAll(it.map { it.username }) }

//        userViewModel.liveData.observeForever {
//            logd(it)
//            binding.username.editText?.setText(it)
//        }
//        //  在主线程中使用
//        userViewModel.liveData.setValue("34234")
//        // 在所有线程中都可使用
////        userViewModel.liveData.postValue("123123")
    }
}