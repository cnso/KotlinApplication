package org.jash.roomdemo

import android.os.Bundle
import android.view.MenuItem
import android.widget.SeekBar
import android.widget.Toast
import com.google.android.material.behavior.SwipeDismissBehavior
import org.jash.common.activity.BaseActivity
import org.jash.common.adapter.CommonAdapter
import org.jash.common.annotations.BindingLayout
import org.jash.common.annotations.Subscribe
import org.jash.common.processor
import org.jash.roomdemo.databinding.ActivityMain3Binding
import org.jash.roomdemo.model.Cart
import org.jash.roomdemo.model.CartList

class MainActivity3 : BaseActivity() {
    @set:BindingLayout("activity_main3")
    lateinit var binding: ActivityMain3Binding
    lateinit var adapter: CommonAdapter<Cart>
    lateinit var list:CartList
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main3)
        val service = retrofit.create(GoodService::class.java)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        list = CartList()
        adapter = CommonAdapter(mapOf(Cart::class.java to (R.layout.cart_item to BR.cart)), list.data)
        binding.adapter = adapter
        binding.list = list
        service.getCarts().subscribe {
            it.data.forEach(processor::onNext)
        }
    }
    @Subscribe
    fun loadCart(cart: Cart) {
        cart.cartList = list
        adapter += cart
    }
    @Subscribe
    fun showMessage(s:String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }
    @Subscribe
    fun action(pair: Pair<String, Int>) {
        when(pair.first) {
            "removeCart" -> adapter.removeIf{it.id == pair.second}
            else -> {}
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}