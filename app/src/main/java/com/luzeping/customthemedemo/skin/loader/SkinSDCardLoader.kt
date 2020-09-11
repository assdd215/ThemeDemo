package com.luzeping.customthemedemo.skin.loader

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import com.luzeping.customthemedemo.skin.SkinCompatManager
import com.luzeping.customthemedemo.skin.SkinLoaderStrategy
import com.luzeping.customthemedemo.skin.util.SkinFileUtils

abstract class SkinSDCardLoader : SkinLoaderStrategy {


    override fun loadSkinInBackground(context: Context?, skinName: String?): String? {
        if (skinName.isNullOrEmpty()) return skinName

        val skinPkgPath = getSkinPath(context, skinName)

        if (SkinFileUtils.isFileExists(skinPkgPath)) {
            val pkgName = SkinCompatManager.getInstance()!!.getSkinPackageName(skinPkgPath)
            val resource = SkinCompatManager.getInstance()!!.getSkinResources(skinPkgPath)

            if (resource != null && pkgName.isNotEmpty()) {
                //TODO                 SkinCompatResources.getInstance().setupSkin(
                //                        resources,
                //                        pkgName,
                //                        skinName,
                //                        this);
                return skinName
            }
        }

        return null
    }

    abstract fun getSkinPath(context: Context?, skinName: String) : String?

    override fun getTargetResourceEntryName(
        context: Context?,
        skinName: String?,
        resId: Int
    ): String? {
        return null
    }

    override fun getColor(context: Context?, skinName: String?, resId: Int): ColorStateList? {
        return null
    }

    override fun getColorStateList(
        context: Context?,
        skinName: String?,
        resId: Int
    ): ColorStateList? {
        return null
    }

    override fun getDrawable(context: Context?, skinName: String?, resId: Int): Drawable? {
        return null
    }

}