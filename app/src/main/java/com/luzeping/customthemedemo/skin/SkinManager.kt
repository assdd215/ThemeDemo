package com.luzeping.customthemedemo.skin

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.lang.RuntimeException
import java.lang.ref.WeakReference

/**
 *  author : luzeping
 *  date   : 2020/8/28
 *  desc   :
 */

class SkinManager {


    private val mActivityRefs = HashSet<WeakReference<Activity>>()

    private var mInit = false

    private var mSkinResource: SkinResource? = null

    private val lifecycleCallback = object : ActivityLifecycleCallbackAdapter() {

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            super.onActivityCreated(activity, savedInstanceState)
        }

        override fun onActivityDestroyed(activity: Activity) {
            removeActivity(activity)
        }

    }

    companion object {

        val INSTANCE by lazy { SkinManager() }
    }

    fun init(application: Application) {
        mInit = true

        mSkinResource = SkinResource(application.applicationContext)
        application.unregisterActivityLifecycleCallbacks(lifecycleCallback)
        application.registerActivityLifecycleCallbacks(lifecycleCallback)
    }

    fun installSkin(skinFilePath: String) {
        checkInit()

        if (mSkinResource?.loadSkin(skinFilePath) == true) {
            notifySkinChange()
        }

    }


    private fun checkInit() {
        if (!mInit) throw RuntimeException("please call init() first")
    }

    private fun notifySkinChange() {

    }

    private fun removeActivity(activity: Activity) {

        mActivityRefs.iterator().run {
            while (hasNext()) {

                val ref = next()

                if (ref.get() == null) remove()

                else if (activity == ref.get()) {
                    remove()
                    break
                }

            }
        }

    }


}