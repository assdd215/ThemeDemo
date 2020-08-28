package com.luzeping.customthemedemo.skin

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Configuration
import android.content.res.Resources
import com.luzeping.customthemedemo.skin.util.FileUtils
import java.io.File
import java.lang.Exception

/**
 *  author : luzeping
 *  date   : 2020/8/28
 *  desc   :
 */


class SkinResource(context: Context) {

    companion object {

        private const val FLAG_RESOURCE_NOT_FOUND = -1

        private const val SKIN_DIR = "skin"
        private const val SKIN_FILE_SUFFIX = ".skin"
        private const val ADD_ASSET_PATH = "addAssetPath"

    }

    private val mContext = context.applicationContext

    private var mSkinResource: Resources? = null

    private var mSkinPackageName: String? = null

    fun loadSkin(skinFilePath: String): Boolean {

        copySkinFile(skinFilePath)?.let {

            val packageName = loadInnerSkin(it.absolutePath)

            if (packageName.isNullOrEmpty()) return false

            mSkinPackageName = packageName

            return true
        }

        return false

    }

    /**
     * 设置显示默认皮肤， 即使用app内置资源
     *
     * @return true:需要刷新界面显示， false:不需要
     */
    fun showDefaultSkin() : Boolean {
        if (mSkinResource == null) return false
        mSkinResource = null
        return true
    }

    fun getDefaultResource(): Resources = mContext.resources

    fun getRealResource(resId: Int): Resources {

        mSkinResource?.let { if (findSkinResId(resId) > 0) return it }

        return mContext.resources
    }

    fun getRealUsedResId(resId: Int) : Int{

        findSkinResId(resId).takeIf { it > 0 } ?.run { return this }

        return resId
    }

    /**
     * 动态资源映射 将app内置的资源Id，转换成 皮肤包内 的资源id
     *
     * @param resId 资源ID值
     * @return 没有皮肤包，或皮肤包未找到资源 返回-1， 否则返回皮肤包内 资源Id
     */
    private fun findSkinResId(resId: Int) : Int{
        if (mSkinResource == null) return FLAG_RESOURCE_NOT_FOUND // 没有皮肤包

        //通过资源的 Name和Type，动态映射，找出皮肤包内 对应资源Id

        val sysResource = mContext.resources

        //资源名称
        val resourceName = sysResource.getResourceEntryName(resId)
        //资源类型
        val resourceType = sysResource.getResourceTypeName(resId)

        val skinResId = mSkinResource!!.getIdentifier(resourceName, resourceType, mSkinPackageName)

        if (skinResId > 0) {
            //找到对应资源
            return skinResId
        }

        return FLAG_RESOURCE_NOT_FOUND
    }

    private fun copySkinFile(skinFilePath: String) : File? {

        if (skinFilePath.isEmpty() || !skinFilePath.endsWith(SKIN_FILE_SUFFIX)) return null

        val file = File(skinFilePath).apply { if (!exists()) return null }

        val targetFile = File(mContext.getDir(SKIN_DIR, Context.MODE_PRIVATE).absolutePath + File.separator + file.name)
            .apply { if (exists() && !delete()) return null }

        if (!FileUtils.copyFile(file, targetFile)) return null

        return targetFile

    }

    /**
     * 从私有目录加载 皮肤文件, 并缓存解析出来的Resource文件
     *
     * @return 加载成功，返回皮肤包名； 加载失败，返回null
     */
    private fun loadInnerSkin(skinFile: String) : String? {

        try {
            val sysResources = mContext.resources

            val assetManager = AssetManager::class.java.newInstance()

            AssetManager::class.java.getMethod(ADD_ASSET_PATH, String::class.java).run {
                isAccessible = true
                invoke(assetManager, skinFile)
            }

            val resources = Resources(assetManager, sysResources.displayMetrics, sysResources.configuration)

            // 根据apk文件路径（皮肤包也是apk文件），获取该应用的包名。兼容5.0 - 9.0（亲测）
            val skinPackageName = mContext.packageManager.getPackageArchiveInfo(skinFile, PackageManager.GET_ACTIVITIES)?.packageName

            if (!skinPackageName.isNullOrEmpty()) {
                mSkinResource = resources
                return skinPackageName
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null

    }
}