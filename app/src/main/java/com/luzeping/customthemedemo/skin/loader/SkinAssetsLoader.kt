package com.luzeping.customthemedemo.skin.loader

import android.content.Context
import com.luzeping.customthemedemo.skin.SkinCompatManager
import com.luzeping.customthemedemo.skin.util.SkinConstants
import com.luzeping.customthemedemo.skin.util.SkinFileUtils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class SkinAssetsLoader(override val type: Int = SkinCompatManager.SKIN_LOADER_STRATEGY_ASSETS) : SkinSDCardLoader() {

    override fun getSkinPath(context: Context?, skinName: String): String? {
        return copySkinFromAssets(context!!, skinName)
    }

    private fun copySkinFromAssets(context: Context, name: String) : String{

        val skinPath = File(SkinFileUtils.getSkinDir(context), name).absolutePath

        try {

            val inputStream = context.assets.open(SkinConstants.SKIN_DEPLOY_PATH + File.separator + name)

            val os = FileOutputStream(skinPath)

            val bytes = ByteArray(1024)

            var byteCount = inputStream.read(bytes)

            while (byteCount != -1) {
                os.write(bytes, 0, byteCount)
                byteCount = inputStream.read(bytes)
            }

            os.close()
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return skinPath

    }

}