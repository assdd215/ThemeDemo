package com.luzeping.customthemedemo.skin.app

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.luzeping.customthemedemo.skin.SkinCompatManager
import com.luzeping.customthemedemo.skin.widget.SkinCompatSupportable
import java.lang.ref.WeakReference
import java.util.concurrent.CopyOnWriteArrayList

class SkinCompatDelegate private constructor(private val mContext: Context): LayoutInflater.Factory2 {

    companion object {
        fun create(context: Context) = SkinCompatDelegate(context)
    }


    private val mSkinCompatViewInflater by lazy { SkinCompatViewInflater() }

    private val mSkinHelpers = CopyOnWriteArrayList<WeakReference<SkinCompatSupportable>>()

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {

        val view = createView(parent, name, context, attrs)

        if (view is SkinCompatSupportable) {
            mSkinHelpers.add(WeakReference(view))
        }
        return view
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {

        val view = createView(null, name, context, attrs)

        if (view is SkinCompatSupportable) {
            mSkinHelpers.add(WeakReference(view))
        }
        return view
    }

    private fun createView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? {

        val wrapperList = SkinCompatManager.getInstance().getWrappers()

        var newContext = context
        for(wrapper in wrapperList) {

            val wrappedContext = wrapper.wrapContext(mContext, parent, attrs)

            if (wrappedContext != null) newContext = wrappedContext

        }

        return mSkinCompatViewInflater.createView(parent, name, newContext, attrs)

    }

    fun applySkin() {
        if (mSkinHelpers.isNotEmpty()) {
            for (ref in mSkinHelpers) {
                ref?.get()?.applySkin()
            }
        }
    }

}