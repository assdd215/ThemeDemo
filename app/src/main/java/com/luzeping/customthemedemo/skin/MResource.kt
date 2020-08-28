package com.luzeping.customthemedemo.skin

import android.annotation.SuppressLint
import android.content.res.*
import android.graphics.Movie
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.annotation.RequiresApi
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

/**
 *  author : luzeping
 *  date   : 2020/8/28
 *  desc   :
 */


class MResource(private val mSkinResource: SkinResource) :
    Resources(
        mSkinResource.getDefaultResource().assets,
        mSkinResource.getDefaultResource().displayMetrics,
        mSkinResource.getDefaultResource().configuration
    ) {

    @Throws(NotFoundException::class)
    override fun getText(id: Int): CharSequence {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getText(realUsedResId)
    }

    @RequiresApi(26)
    @Throws(NotFoundException::class)
    override fun getFont(id: Int): Typeface {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getFont(realUsedResId)
    }

    @Throws(NotFoundException::class)
    override fun getQuantityText(id: Int, quantity: Int): CharSequence {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getQuantityText(realUsedResId, quantity)
    }

    @Throws(NotFoundException::class)
    override fun getString(id: Int): String {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getString(realUsedResId)
    }

    @Throws(NotFoundException::class)
    override fun getString(id: Int, vararg formatArgs: Any?): String {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getString(realUsedResId, *formatArgs)
    }

    @Throws(NotFoundException::class)
    override fun getQuantityString(
        id: Int,
        quantity: Int,
        vararg formatArgs: Any?
    ): String {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getQuantityString(realUsedResId, quantity, *formatArgs)
    }

    @Throws(NotFoundException::class)
    override fun getQuantityString(id: Int, quantity: Int): String {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getQuantityString(realUsedResId, quantity)
    }

    override fun getText(id: Int, def: CharSequence?): CharSequence {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getText(realUsedResId, def)
    }

    @Throws(NotFoundException::class)
    override fun getTextArray(id: Int): Array<CharSequence> {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getTextArray(realUsedResId)
    }

    @Throws(NotFoundException::class)
    override fun getStringArray(id: Int): Array<String> {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getStringArray(realUsedResId)
    }

    @Throws(NotFoundException::class)
    override fun getIntArray(id: Int): IntArray {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getIntArray(realUsedResId)
    }

    @Throws(NotFoundException::class)
    override fun obtainTypedArray(id: Int): TypedArray {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.obtainTypedArray(realUsedResId)
    }

    @Throws(NotFoundException::class)
    override fun getDimension(id: Int): Float {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getDimension(realUsedResId)
    }

    @Throws(NotFoundException::class)
    override fun getDimensionPixelOffset(id: Int): Int {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getDimensionPixelOffset(realUsedResId)
    }

    @Throws(NotFoundException::class)
    override fun getDimensionPixelSize(id: Int): Int {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getDimensionPixelSize(realUsedResId)
    }

    override fun getFraction(id: Int, base: Int, pbase: Int): Float {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getFraction(realUsedResId, base, pbase)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Throws(NotFoundException::class)
    override fun getDrawable(id: Int): Drawable {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getDrawable(realUsedResId, null)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(21)
    @Throws(NotFoundException::class)
    override fun getDrawable(id: Int, theme: Theme?): Drawable {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getDrawable(realUsedResId, theme)
    }

    @Throws(NotFoundException::class)
    override fun getDrawableForDensity(id: Int, density: Int): Drawable? {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getDrawableForDensity(realUsedResId, density, null)
    }

    @RequiresApi(21)
    override fun getDrawableForDensity(id: Int, density: Int, theme: Theme?): Drawable? {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getDrawableForDensity(realUsedResId, density, theme)
    }

    @Throws(NotFoundException::class)
    override fun getMovie(id: Int): Movie {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getMovie(realUsedResId)
    }

    @Throws(NotFoundException::class)
    override fun getColor(id: Int): Int {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getColor(realUsedResId)
    }

    @RequiresApi(23)
    @Throws(NotFoundException::class)
    override fun getColor(id: Int, theme: Theme?): Int {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getColor(realUsedResId, theme)
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    @Throws(NotFoundException::class)
    override fun getColorStateList(id: Int): ColorStateList {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getColorStateList(realUsedResId)
    }

    @RequiresApi(23)
    @Throws(NotFoundException::class)
    override fun getColorStateList(id: Int, theme: Theme?): ColorStateList {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getColorStateList(realUsedResId, theme)
    }

    @Throws(NotFoundException::class)
    override fun getBoolean(id: Int): Boolean {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getBoolean(realUsedResId)
    }

    @Throws(NotFoundException::class)
    override fun getInteger(id: Int): Int {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getInteger(realUsedResId)
    }

    @Throws(NotFoundException::class)
    override fun getLayout(id: Int): XmlResourceParser {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getLayout(realUsedResId)
    }

    @Throws(NotFoundException::class)
    override fun getAnimation(id: Int): XmlResourceParser {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getAnimation(realUsedResId)
    }

    @Throws(NotFoundException::class)
    override fun getXml(id: Int): XmlResourceParser {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.getXml(realUsedResId)
    }

    @Throws(NotFoundException::class)
    override fun openRawResource(id: Int): InputStream {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.openRawResource(realUsedResId)
    }

    @Throws(NotFoundException::class)
    override fun openRawResource(id: Int, value: TypedValue?): InputStream {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.openRawResource(realUsedResId, value)
    }

    @Throws(NotFoundException::class)
    override fun openRawResourceFd(id: Int): AssetFileDescriptor {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        return resource.openRawResourceFd(realUsedResId)
    }

    @Throws(NotFoundException::class)
    override fun getValue(
        id: Int,
        outValue: TypedValue?,
        resolveRefs: Boolean
    ) {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        resource.getValue(realUsedResId, outValue, resolveRefs)
    }

    @Throws(NotFoundException::class)
    override fun getValueForDensity(
        id: Int,
        density: Int,
        outValue: TypedValue?,
        resolveRefs: Boolean
    ) {
        val resource = mSkinResource.getRealResource(id)
        val realUsedResId = mSkinResource.getRealUsedResId(id)
        resource.getValueForDensity(realUsedResId, density, outValue, resolveRefs)
    }

    @Throws(NotFoundException::class)
    override fun getValue(
        name: String?,
        outValue: TypedValue?,
        resolveRefs: Boolean
    ) {
        mSkinResource.getDefaultResource().getValue(name, outValue, resolveRefs)
    }

    override fun obtainAttributes(
        set: AttributeSet?,
        attrs: IntArray?
    ): TypedArray {
        return mSkinResource.getDefaultResource().obtainAttributes(set, attrs)
    }

    override fun updateConfiguration(
        config: Configuration?,
        metrics: DisplayMetrics?
    ) {
        mSkinResource.getDefaultResource().updateConfiguration(config, metrics)
    }

    override fun getDisplayMetrics(): DisplayMetrics {
        return mSkinResource.getDefaultResource().displayMetrics
    }

    override fun getConfiguration(): Configuration {
        return mSkinResource.getDefaultResource().configuration
    }

    override fun getIdentifier(
        name: String?,
        defType: String?,
        defPackage: String?
    ): Int {
        return mSkinResource.getDefaultResource().getIdentifier(name, defType, defPackage)
    }

    @Throws(NotFoundException::class)
    override fun getResourceName(resid: Int): String {
        val resource = mSkinResource.getRealResource(resid)
        val realUsedResId = mSkinResource.getRealUsedResId(resid)
        return resource.getResourceName(realUsedResId)
    }

    @Throws(NotFoundException::class)
    override fun getResourcePackageName(resid: Int): String {
        val resource = mSkinResource.getRealResource(resid)
        val realUsedResId = mSkinResource.getRealUsedResId(resid)
        return resource.getResourcePackageName(realUsedResId)
    }

    @Throws(NotFoundException::class)
    override fun getResourceTypeName(resid: Int): String {
        val resource = mSkinResource.getRealResource(resid)
        val realUsedResId = mSkinResource.getRealUsedResId(resid)
        return resource.getResourceTypeName(realUsedResId)
    }

    @Throws(NotFoundException::class)
    override fun getResourceEntryName(resid: Int): String {
        val resource = mSkinResource.getRealResource(resid)
        val realUsedResId = mSkinResource.getRealUsedResId(resid)
        return resource.getResourceEntryName(realUsedResId)
    }

    @Throws(IOException::class, XmlPullParserException::class)
    override fun parseBundleExtras(
        parser: XmlResourceParser?,
        outBundle: Bundle?
    ) {
        mSkinResource.getDefaultResource().parseBundleExtras(parser, outBundle)
    }

    @Throws(XmlPullParserException::class)
    override fun parseBundleExtra(
        tagName: String?,
        attrs: AttributeSet?,
        outBundle: Bundle?
    ) {
        mSkinResource.getDefaultResource().parseBundleExtra(tagName, attrs, outBundle)
    }

}
