package com.luzeping.customthemedemo.skin

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.util.SparseArray
import com.luzeping.customthemedemo.skin.app.SkinActivityLifecycle
import com.luzeping.customthemedemo.skin.app.SkinLayoutInflater
import com.luzeping.customthemedemo.skin.app.SkinWrapper
import com.luzeping.customthemedemo.skin.loader.SkinAssetsLoader
import com.luzeping.customthemedemo.skin.loader.SkinBuildInLoader
import com.luzeping.customthemedemo.skin.loader.SkinNoneLoader
import com.luzeping.customthemedemo.skin.loader.SkinPrefixBuildInLoader
import com.luzeping.customthemedemo.skin.res.SkinCompatResources
import com.luzeping.customthemedemo.skin.util.SkinPreference

class SkinCompatManager(context: Context) : SkinObservable() {

    val mAppContext: Context = context.applicationContext
    init {
        initLoaderStrategy()
    }

    private val mStrategyMap = SparseArray<SkinLoaderStrategy?>()
    private val mWrappers = ArrayList<SkinWrapper>()
    private val mInflaters = ArrayList<SkinLayoutInflater>()
    private val mHookInflaters = ArrayList<SkinLayoutInflater>()

    private val mLock = Object()

    private var mLoading = false

    companion object {

        const val SKIN_LOADER_STRATEGY_NONE = -1
        const val SKIN_LOADER_STRATEGY_ASSETS = 0
        const val SKIN_LOADER_STRATEGY_BUILD_IN = 1
        const val SKIN_LOADER_STRATEGY_PREFIX_BUILD_IN = 2

        private var instance : SkinCompatManager? = null

        fun getInstance() = instance!!

        fun init(context: Context): SkinCompatManager {
            if (instance == null) {
                synchronized(SkinCompatManager::class.java) {
                    if (instance == null) instance = SkinCompatManager(context)
                }
            }

            return instance!!
        }

        fun withoutActivity(application: Application) : SkinCompatManager {
            init(application)
            SkinActivityLifecycle.init(application)
            return instance!!
        }
    }

    private fun initLoaderStrategy() {
        mStrategyMap.put(SKIN_LOADER_STRATEGY_NONE, SkinNoneLoader())
        mStrategyMap.put(SKIN_LOADER_STRATEGY_ASSETS, SkinAssetsLoader())
        mStrategyMap.put(SKIN_LOADER_STRATEGY_BUILD_IN, SkinBuildInLoader())
        mStrategyMap.put(SKIN_LOADER_STRATEGY_PREFIX_BUILD_IN, SkinPrefixBuildInLoader())
    }

    fun loadSkin() : AsyncTask<String, Any, String?>? {

        val skin = SkinPreference.getInstance().skinName

        val strategy = SkinPreference.getInstance().skinStrategy

        if (skin.isNullOrEmpty() || strategy == SKIN_LOADER_STRATEGY_NONE) {
            return null
        }
        return loadSkin(skin, null, strategy)
    }

    fun loadSkin(skinName: String?, listener: SkinLoaderListener?, strategy: Int) :AsyncTask<String, Any, String?>?{
        val loaderStrategy = mStrategyMap.get(strategy) ?: return null

        return SkinLoaderTask(listener, loaderStrategy)
            .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, skinName)
    }

    fun getContext(): Context = mAppContext

    fun getWrappers() = mWrappers

    fun getInflaters() = mInflaters

    fun getHookInflaters() = mHookInflaters

    fun getSkinPackageName(skinPkgPath: String?): String {
        val packageManager = mAppContext.packageManager
        val info = packageManager.getPackageArchiveInfo(skinPkgPath ?: "", PackageManager.GET_ACTIVITIES)
        return info?.packageName ?: ""
    }

    /**
     * 获取皮肤包资源{@link Resources}.
     *
     * @param skinPkgPath sdcard中皮肤包路径.
     * @return
     */
    fun getSkinResources(skinPkgPath: String?): Resources? {

        try {

            val packageInfo = mAppContext.packageManager.getPackageArchiveInfo(skinPkgPath ?:"", 0)
            packageInfo!!.apply {
                applicationInfo.sourceDir  = skinPkgPath
                applicationInfo.publicSourceDir = skinPkgPath
            }
            val res = mAppContext.packageManager.getResourcesForApplication(packageInfo.applicationInfo)
            val superRes = mAppContext.resources
            return Resources(res.assets, superRes.displayMetrics, superRes.configuration)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null

    }



    private inner class SkinLoaderTask(val mListener: SkinLoaderListener?,
                                 val mStrategy: SkinLoaderStrategy)
        : AsyncTask<String, Any, String?>() {



        override fun onPreExecute() {
            mListener?.onStart()
        }

        override fun doInBackground(vararg params: String?): String? {
            synchronized(mLock) {
                while (mLoading) {
                    try {
                        mLock.wait()
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }

                mLoading = true
            }
            try {

                if (params.size == 1) {
                    val skinName = mStrategy.loadSkinInBackground(mAppContext, params[0])
                    if (skinName.isNullOrEmpty()) {
                        SkinCompatResources.getInstance().reset(mStrategy);
                        return ""
                    }

                    return params[0]
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
            SkinCompatResources.getInstance().reset();
            return null
        }

        override fun onPostExecute(result: String?) {
            synchronized(mLock) {
                if (result.isNullOrEmpty()) {
                    SkinPreference.getInstance()
                        .setSkinName("")
                        .setSkinStrategy(SKIN_LOADER_STRATEGY_NONE)
                        .commitEditor()
                    mListener?.onFailed("皮肤资源获取失败")

                } else {
                    SkinPreference.getInstance()
                        .setSkinName(result)
                        .setSkinStrategy(mStrategy.type)
                        .commitEditor()
                    notifyUpdateSkin()
                    mListener?.onSuccess()

                }

                mLoading = false
                mLock.notifyAll()
            }
        }
    }

}



/**
 * 皮肤包加载监听.
 */
interface SkinLoaderListener {
    /**
     * 开始加载.
     */
    fun onStart()

    /**
     * 加载成功.
     */
    fun onSuccess()

    /**
     * 加载失败.
     *
     * @param errMsg 错误信息.
     */
    fun onFailed(errMsg: String?)
}

/**
 * 皮肤包加载策略.
 */
interface SkinLoaderStrategy {
    /**
     * 加载皮肤包.
     *
     * @param context  [Context]
     * @param skinName 皮肤包名称.
     * @return 加载成功，返回皮肤包名称；失败，则返回空。
     */
    fun loadSkinInBackground(
        context: Context?,
        skinName: String?
    ): String?

    /**
     * 根据应用中的资源ID，获取皮肤包相应资源的资源名.
     *
     * @param context  [Context]
     * @param skinName 皮肤包名称.
     * @param resId    应用中需要换肤的资源ID.
     * @return 皮肤包中相应的资源名.
     */
    fun getTargetResourceEntryName(
        context: Context?,
        skinName: String?,
        resId: Int
    ): String?

    /**
     * 开发者可以拦截应用中的资源ID，返回对应color值。
     *
     * @param context  [Context]
     * @param skinName 皮肤包名称.
     * @param resId    应用中需要换肤的资源ID.
     * @return 获得拦截后的颜色值，添加到ColorStateList的defaultColor中。不需要拦截，则返回空
     */
    fun getColor(
        context: Context?,
        skinName: String?,
        resId: Int
    ): ColorStateList?

    /**
     * 开发者可以拦截应用中的资源ID，返回对应ColorStateList。
     *
     * @param context  [Context]
     * @param skinName 皮肤包名称.
     * @param resId    应用中需要换肤的资源ID.
     * @return 返回对应ColorStateList。不需要拦截，则返回空
     */
    fun getColorStateList(
        context: Context?,
        skinName: String?,
        resId: Int
    ): ColorStateList?

    /**
     * 开发者可以拦截应用中的资源ID，返回对应Drawable。
     *
     * @param context  [Context]
     * @param skinName 皮肤包名称.
     * @param resId    应用中需要换肤的资源ID.
     * @return 返回对应Drawable。不需要拦截，则返回空
     */
    fun getDrawable(
        context: Context?,
        skinName: String?,
        resId: Int
    ): Drawable?

    /**
     * [.SKIN_LOADER_STRATEGY_NONE]
     * [.SKIN_LOADER_STRATEGY_ASSETS]
     * [.SKIN_LOADER_STRATEGY_BUILD_IN]
     * [.SKIN_LOADER_STRATEGY_PREFIX_BUILD_IN]
     *
     * @return 皮肤包加载策略类型.
     */
    val type: Int
}