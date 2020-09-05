package com.luzeping.customthemedemo

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.luzeping.customthemedemo.skin.SkinManager
import kotlinx.android.synthetic.main.activity_main.*

class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text_view.setBackgroundColor(resources.getColor(R.color.color_text_view))

        Log.d("Lzp",getDir("myDir", Context.MODE_PRIVATE).path)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        SkinManager.INSTANCE.createActivityResourceProxy(newBase!!)
    }

    override fun getResources(): Resources {
        return super.getResources()
    }

    override fun createConfigurationContext(overrideConfiguration: Configuration): Context {
        return super.createConfigurationContext(overrideConfiguration)
    }
}