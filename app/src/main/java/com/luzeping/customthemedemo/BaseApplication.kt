package com.luzeping.customthemedemo

import android.app.Application
import android.content.res.Resources
import android.os.Environment
import com.luzeping.customthemedemo.skin.SkinCompatManager
import com.luzeping.customthemedemo.skin.SkinManager

class BaseApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        SkinCompatManager.withoutActivity(this)

    }

    override fun getResources(): Resources {
        return super.getResources()
    }
}