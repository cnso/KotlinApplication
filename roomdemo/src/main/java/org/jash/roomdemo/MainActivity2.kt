package org.jash.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jash.common.activity.BaseActivity
import org.jash.common.activity.SafeSubscribe
import org.jash.common.adapter.CommonAdapter
import org.jash.common.annotations.Subscribe
import org.jash.common.proscessor
import org.jash.roomdemo.databinding.ActivityMain2Binding
import org.jash.roomdemo.model.Category
import org.jash.roomdemo.repository.ApiRepository
import org.jash.roomdemo.viewmodel.Main2ViewModel

class MainActivity2 : BaseActivity() {
    lateinit var adapter: CommonAdapter<Category>
    lateinit var binding:ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView<ActivityMain2Binding>(this, R.layout.activity_main2)
        val main2ViewModel by viewModels<Main2ViewModel> ()
        adapter =
            CommonAdapter(mapOf(Category::class.java to (R.layout.item to BR.category)), main2ViewModel.category)
        binding.adapter = adapter
//        val safeSubscribe = SafeSubscribe(
//            proscessor.ofType(String::class.java)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//                    when (it) {
//                        "clearCategory" -> adapter.clear()
//                        else -> Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
//                    }
//                },
//            proscessor.ofType(Category::class.java)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//                    binding.refresh.isRefreshing = false
//                    adapter += it
//                           },
//
//        )
        val apiRepository = ApiRepository(database, retrofit)
        if (main2ViewModel.category.isEmpty()) {
            apiRepository.loadCategory(1, loadLocal = true)
//            retrofit.create(GoodService::class.java).getCategory(1)
//                .subscribe {
//                    logd("网络请求")
//                    proscessor.onNext("clearCategory")
//                    it.data.forEach(proscessor::onNext)
//                }
//            database.getCategoryDao().getCategory(1)
//                .subscribeOn(Schedulers.io())
//                .subscribe {
//                    logd(it.toString())
//                    it.forEach(proscessor::onNext)
//                }
        }
        binding.refresh.setColorSchemeColors(0xff0000, 0x00ffff, 0x00ff00)
        binding.refresh.setOnRefreshListener {
            adapter.clear()
            apiRepository.loadCategory(1)
        }

    }
    @Subscribe
    fun loadMessage(s:String) {
        when (s) {
            "clearCategory" -> adapter.clear()
            else -> Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
        }
    }
    @Subscribe
    fun displayCategory(category: Category) {
        binding.refresh.isRefreshing = false
        adapter += category
    }
}