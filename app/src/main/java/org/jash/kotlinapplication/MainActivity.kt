package org.jash.kotlinapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
//    lateinit var myData: MyData
    var num = 0;
    lateinit var tv:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logd("onCreate")
//        myData = MyData()
//        lifecycle.addObserver(myData)
//        myData.load()
//        LifecycleOwner
//        LifecycleObserver
//        findViewById<Button>(R.id.start_service).setOnClickListener {
//            startService(Intent(this, MyService::class.java))
//        }
//        findViewById<Button>(R.id.stop_service).setOnClickListener {
//            stopService(Intent(this, MyService::class.java))
//        }
        num = savedInstanceState?.getInt("num") ?: 0
        tv = findViewById<TextView>(R.id.text).apply { text = num.toString() }
        findViewById<Button>(R.id.button).setOnClickListener { tv.text = (num++).toString() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("num", num)
    }

    override fun onStart() {
        super.onStart()
        logd("onStart")
    }

    override fun onResume() {
        super.onResume()
        logd("onResume")
    }

    override fun onPause() {
        super.onPause()
        logd("onPause")
    }

    override fun onStop() {
        super.onStop()
        logd("onStop")
//        myData.release()
    }

    override fun onDestroy() {
        super.onDestroy()
        logd("onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        logd("onRestart")
    }
}