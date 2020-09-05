package com.luzeping.customthemedemo.skin

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.luzeping.customthemedemo.skin.util.ActionBarUtils
import com.luzeping.customthemedemo.skin.util.NavigationUtils
import com.luzeping.customthemedemo.skin.util.StatusBarUtils
import java.lang.Exception
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

    private var mResource: MResource? = null

    var mThemeColorId = -1

    private val lifecycleCallback = object : ActivityLifecycleCallbackAdapter() {

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            applySkinTheme(activity)
            createActivityResourceProxy(activity)
        }

        override fun onActivityDestroyed(activity: Activity) {
            removeActivity(activity)
        }

    }

    companion object {

        private const val  CONTEXT_IMPL_CLASS_NAME = "android.view.ContextThemeWrapper"
        private const val CONTEXT_IMPL_FIELD_NAME = "mResources"

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

    fun createActivityResourceProxy(context: Context) {

        val currentMillis = System.currentTimeMillis()

        try {
            @SuppressLint("PrivateApi")
            val clazz = Class.forName(CONTEXT_IMPL_CLASS_NAME)
            val field = clazz.getDeclaredField(CONTEXT_IMPL_FIELD_NAME)
            field.isAccessible = true

            if (mResource == null) mResource = MResource(mSkinResource!!)

            field.set(context, mResource)

        }catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun notifySkinChange() {
        val currentMillis = System.currentTimeMillis()

        for (reference in mActivityRefs) {
            reference.get()?.let {

                applySkinTheme(it)

                it.window?.decorView?.run {
                    requestLayout()
                    invalidate()
                }
            }
        }
    }

    private fun checkInit() {
        if (!mInit) throw RuntimeException("please call init() first")
    }

    private fun applySkinTheme(activity: Activity) {
        checkInit()

        if (mThemeColorId <= 0) return

        val resource = mSkinResource?.getRealResource(mThemeColorId) ?: return
        val realUsedResId = mSkinResource?.getRealUsedResId(mThemeColorId) ?: return
        val themeColor = resource.getColor(realUsedResId)

        StatusBarUtils.forStatusBar(activity, themeColor)
        NavigationUtils.forNavigation(activity, themeColor)

        if (activity is AppCompatActivity) ActionBarUtils.forActionBar(activity, themeColor)
    }

    fun showDefaultSkin() {
        checkInit()

        if (mSkinResource?.showDefaultSkin() == true) notifySkinChange()
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