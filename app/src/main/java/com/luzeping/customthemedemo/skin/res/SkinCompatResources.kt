package com.luzeping.customthemedemo.skin.res

import android.content.res.Resources
import com.luzeping.customthemedemo.skin.SkinCompatManager
import com.luzeping.customthemedemo.skin.SkinLoaderListener
import com.luzeping.customthemedemo.skin.SkinLoaderStrategy

class SkinCompatResources {

    private var mResources : Resources? = null
    private var mStrategy: SkinLoaderStrategy? = null
    private var isDefaultSkin = true
    private val mSkinResources = ArrayList<SkinResources>()
    private var mSkinPkgName: String? = ""
    private var mSkinName: String? = ""

    companion object {
        fun getInstance() = INSTANCE.instance
    }

    private object INSTANCE {
        val instance = SkinCompatResources()
    }

    fun reset() {

    }

    fun reset(strategy: SkinLoaderStrategy?) {

        mResources = SkinCompatManager.getInstance()?.getContext()?.resources

        mSkinName = ""
        mSkinPkgName = ""
        mStrategy = strategy
        isDefaultSkin = true
        //TODO SkinCompatUserThemeManager.get().clearCaches();
        for (skinResources in mSkinResources) skinResources.clear()
    }

}

interface SkinResources {
    fun clear()
}