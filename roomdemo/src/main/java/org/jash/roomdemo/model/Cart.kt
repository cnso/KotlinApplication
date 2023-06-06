package org.jash.roomdemo.model

import android.content.Context
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.jash.common.BR
import org.jash.common.processor
import org.jash.roomdemo.GoodService
import org.jash.roomdemo.retrofit

class Cart(

    val goods_default_icon: String? = null,
    val goods_default_price: Double? = null,
    val goods_desc: String? = null,
    val goods_id: Int,
    val id: Int? = null,
    val order_id: Int? = null,
    val user_id: Int? = null,
    count:Int

) : BaseObservable() {
    @get:Bindable
    var checked:Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.checked)
            cartList?.notifyPropertyChanged(BR.all)
            cartList?.notifyPropertyChanged(BR.sum)
        }
    var cartList: CartList? = null
    @get:Bindable
    var count: Int = count
        set(value) {
            if (value >= 0){
                field = value
                notifyPropertyChanged(BR.count)
                if(checked)
                    cartList?.notifyPropertyChanged(BR.sum)
            }
        }
    fun addCount(num:Int) {
        count += num
    }
    fun delCart(context: Context):Boolean {
        val service = retrofit.create(GoodService::class.java)
        MaterialAlertDialogBuilder(context)
            .setTitle("确认删除")
            .setMessage("是否要删除$goods_desc?")
            .setPositiveButton("确定", {_,_ ->
                service.delCart(mapOf("ids" to id.toString())).subscribe {
                    if (it.code == 200) {
                        processor.onNext("removeCart" to id)
                    }
                    processor.onNext(it.message)
                }
            })
            .setNegativeButton("取消", null)
            .show()
        return true
    }

}