package com.luzeping.customthemedemo.skin.loader

import android.content.Context
import android.graphics.drawable.Drawable
import com.luzeping.customthemedemo.skin.util.SkinFileUtils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.zip.ZipFile

class ZipSDCardLoader(override val type: Int = SKIN_LOADER_STRATEGY_ZIP) : SkinSDCardLoader() {

    companion object {
        const val SKIN_LOADER_STRATEGY_ZIP = Int.MAX_VALUE - 1
    }

    override fun loadSkinInBackground(context: Context?, skinName: String?): String? {

        try {
            val inputStream = context!!.assets.open("zip_res.zip")

            val dir = SkinFileUtils.getSkinDir(context)

            val file = File(dir, "zip_res.zip")

            val os = FileOutputStream(file)

            var byteCount = 0
            val bytes = ByteArray(1024)
            byteCount = inputStream.read(bytes)
            while (byteCount != -1) {
                os.write(bytes, 0, byteCount)
                byteCount = inputStream.read(bytes)
            }

            inputStream.close()
            os.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }


        return super.loadSkinInBackground(context, skinName)
    }

    @Throws(IOException::class)
    private fun unzipBackgroundPng(dir: String) {

        val zipBackground = File(dir, "zip_background.png")

        val zipFile = ZipFile(File(dir, "zip_res.zip"))

        val entry = zipFile.getEntry("zip_background.png")

        val inputStream = zipFile.getInputStream(entry)

        val os = FileOutputStream(zipBackground)

        var byteCount = 0

        var bytes = ByteArray(1024)

        byteCount = inputStream.read(bytes)

        while (byteCount != -1) {
            os.write(bytes, 0, byteCount)
            byteCount = inputStream.read(bytes)
        }

        os.close()
        inputStream.close()
    }


    override fun getSkinPath(context: Context?, skinName: String): String? {
        return File(SkinFileUtils.getSkinDir(context), skinName).absolutePath
    }

    override fun getDrawable(context: Context?, skinName: String?, resId: Int): Drawable? {

        val resName = context?.resources?.getResourceEntryName(resId)

        val resType = context?.resources?.getResourceTypeName(resId)

        if ("drawable".equals(resType, true) && "zip_background".equals(resName, true)) {
            val dir = SkinFileUtils.getSkinDir(context)

            val zipBackground = File(dir, "zip_background.png")

            if (zipBackground.exists()) return Drawable.createFromPath(zipBackground.absolutePath)

            return null
        }

        return super.getDrawable(context, skinName, resId)
    }

}