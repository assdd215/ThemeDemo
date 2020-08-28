package com.luzeping.customthemedemo.skin

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 *  author : luzeping
 *  date   : 2020/8/28
 *  desc   :
 */

open class ActivityLifecycleCallbackAdapter : Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

}