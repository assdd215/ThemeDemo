package com.luzeping.customthemedemo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("Lzp",getDir("myDir", Context.MODE_PRIVATE).path)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)

    }
}