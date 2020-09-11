package com.luzeping.customthemedemo.skin.loader

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import com.luzeping.customthemedemo.skin.SkinCompatManager
import com.luzeping.customthemedemo.skin.SkinLoaderStrategy

class SkinPrefixBuildInLoader(override val type: Int = SkinCompatManager.SKIN_LOADER_STRATEGY_PREFIX_BUILD_IN) : SkinLoaderStrategy {
    override fun loadSkinInBackground(context: Context?, skinName: String?): String? {
        return skinName
    }

    override fun getTargetResourceEntryName(
        context: Context?,
        skinName: String?,
        resId: Int
    ): String? {
        return skinName + "_" + context?.resources?.getResourceEntryName(resId)
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