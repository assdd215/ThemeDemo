package com.luzeping.customthemedemo.skin.app

import android.app.Activity
import android.app.Application
import android.os.Bundle

class SkinActivityLifecycle(application: Application) : Application.ActivityLifecycleCallbacks {


    companion object {

        private var instance: SkinActivityLifecycle? = null

        fun init(application: Application) :SkinActivityLifecycle{

            if (instance == null) {
                synchronized(SkinActivityLifecycle::class.java) {
                    if (instance == null) {
                        instance = SkinActivityLifecycle(application)
                    }
                }
            }
            return instance!!
        }

    }

    override fun onActivityPaused(activity: Activity) {
        TODO("Not yet implemented")
    }

    override fun onActivityStarted(activity: Activity) {
        TODO("Not yet implemented")
    }

    override fun onActivityDestroyed(activity: Activity) {
        TODO("Not yet implemented")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        TODO("Not yet implemented")
    }

    override fun onActivityStopped(activity: Activity) {
        TODO("Not yet implemented")
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onActivityResumed(activity: Activity) {
        TODO("Not yet implemented")
    }

}