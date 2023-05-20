package org.jash.bindingapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import org.jash.bindingapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val user = User(ObservableField<String>("admin"), "123456")
        val imageBean = ImageBean("https://gimg3.baidu.com/search/src=http%3A%2F%2Fpics3.baidu.com%2Ffeed%2F30adcbef76094b363cc09d85a2074dd58c109d5b.jpeg%40f_auto%3Ftoken%3D11f7f1bc45ed8c2b49b5272dc772270d&refer=http%3A%2F%2Fwww.baidu.com&app=2021&size=f360,240&n=0&g=0n&q=75&fmt=auto?sec=1684688400&t=6a0d2e8ecb035c024ea604a294319a2c", Bitmap.createBitmap(5, 5,Bitmap.Config.ARGB_8888))
        binding.bean = imageBean
        binding.user = user

    }
}