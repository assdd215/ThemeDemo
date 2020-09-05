package com.luzeping.customthemedemo

import android.app.Application
import android.content.res.Resources
import android.os.Environment
import com.luzeping.customthemedemo.skin.SkinManager

class BaseApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        SkinManager.INSTANCE.init(this)

        SkinManager.INSTANCE.installSkin(Environment.getExternalStorageDirectory().toString() + "/zman/app-debug.skin")
    }

    override fun getResources(): Resources {
        return super.getResources()
    }
}