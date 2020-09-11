package com.luzeping.customthemedemo.skin.app

import android.content.Context
import android.content.ContextWrapper
import android.util.ArrayMap
import android.util.AttributeSet
import android.view.View
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.view.ViewCompat
import com.luzeping.customthemedemo.skin.SkinCompatManager
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.jar.Attributes

class SkinCompatViewInflater {



    companion object {

        private val sConstructorSignature = arrayOf(Context::class.java, Attributes::class.java)

        private val sOnClickAttrs = intArrayOf(android.R.attr.onClick)

        private val sClassPrefixList = arrayOf("android.widget.",
            "android.view.",
            "android.webkit.")

        private val sConstructorMap = ArrayMap<String, Constructor<out View>>()

    }

    private val mConstructorArgs = arrayOfNulls<Any>(2)

    fun createView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? {

        var view = createViewFromHackInflater(context, name, attrs)

        if (view == null) {
            view = createViewFromHackInflater(context, name, attrs)
        }

        if (view == null) {
            view = createViewFromTag(context, name, attrs)
        }

        if (view != null) {
            // If we have created a view, check it's android:onClick
            checkOnClickListener(view, attrs)
        }

        return view
    }

    private fun createViewFromHackInflater(context: Context, name: String, attrs: AttributeSet) : View?{

        var view: View? = null

        for (inflater in SkinCompatManager.getInstance().getHookInflaters()) {

            view = inflater.createView(context, name, attrs)
            if (view == null) continue
            break
        }

        return view
    }

    private fun createViewFromInflater(context: Context, name: String, attrs: AttributeSet) : View?{

        var view: View? = null

        for (inflater in SkinCompatManager.getInstance().getInflaters()) {
            view = inflater.createView(context, name, attrs)
            if (view == null) continue
            break
        }

        return view
    }

    fun createViewFromTag(context:Context, name: String, attrs: AttributeSet): View? {
        var newName : String = ""
        if ("view" == name) {
            newName = attrs.getAttributeValue(null, "class")
        }

        try {

            mConstructorArgs[0] = context
            mConstructorArgs[1] = attrs

            if (newName.indexOf('.') == -1) {

                for (i in sClassPrefixList.indices) {
                    val view = createView(context, newName, sClassPrefixList[i])
                    if (view != null) return view
                }
                return null
            } else {
                return createView(context, newName, null)
            }

        } catch (e: Exception) {
            return null
        } finally {
            mConstructorArgs[0] = null
            mConstructorArgs[1] = null

        }
    }

    private fun createView(context:Context, name: String, prefix: String?) : View? {

        try {
            var constructor = sConstructorMap[name]

            if (constructor == null) {

                val clazz = context.classLoader.loadClass(if (prefix != null) prefix + name else name).asSubclass(View::class.java)

                constructor = clazz.getConstructor(*sConstructorSignature)
                sConstructorMap[name] = constructor
            }

            constructor.isAccessible = true
            return constructor.newInstance(mConstructorArgs)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * android:onClick doesn't handle views with a ContextWrapper context. This method
     * backports new framework functionality to traverse the Context wrappers to find a
     * suitable target.
     */
    private fun checkOnClickListener(view: View, attrs: AttributeSet) {

        val context = view.context

        if (context !is ContextWrapper || !ViewCompat.hasOnClickListeners(view)) {
            return
        }

        val typedArray = context.obtainStyledAttributes(attrs, sOnClickAttrs)

        val handlerName = typedArray.getString(0)
        handlerName?.let { view.setOnClickListener(DeclaredOnClickListener(view, handlerName)) }

        typedArray.recycle()
    }


    /**
     * An implementation of OnClickListener that attempts to lazily load a
     * named click handling method from a parent or ancestor context.
     */
    private class DeclaredOnClickListener(
        @param:NonNull private val mHostView: View,
        @param:NonNull private val mMethodName: String
    ) :
        View.OnClickListener {
        private var mResolvedMethod: Method? = null
        private var mResolvedContext: Context? = null
        override fun onClick(@NonNull v: View) {
            if (mResolvedMethod == null) {
                resolveMethod(mHostView.context, mMethodName)
            }
            try {
                mResolvedMethod!!.invoke(mResolvedContext, v)
            } catch (e: IllegalAccessException) {
                throw IllegalStateException(
                    "Could not execute non-public method for android:onClick", e
                )
            } catch (e: InvocationTargetException) {
                throw IllegalStateException(
                    "Could not execute method for android:onClick", e
                )
            }
        }

        @NonNull
        private fun resolveMethod(
            @Nullable context: Context,
            @NonNull name: String
        ) {
            var context: Context? = context
            while (context != null) {
                try {
                    if (!context.isRestricted) {
                        val method = context.javaClass.getMethod(
                            mMethodName,
                            View::class.java
                        )
                        if (method != null) {
                            mResolvedMethod = method
                            mResolvedContext = context
                            return
                        }
                    }
                } catch (e: NoSuchMethodException) {
                    // Failed to find method, keep searching up the hierarchy.
                }
                context = if (context is ContextWrapper) {
                    context.baseContext
                } else {
                    // Can't search up the hierarchy, null out and fail.
                    null
                }
            }
            val id = mHostView.id
            val idText = if (id == View.NO_ID) "" else " with id '"+ mHostView.context.resources.getResourceEntryName(id) + "'"
            throw IllegalStateException(
                "Could not find method " + mMethodName
                        + "(View) in a parent or ancestor Context for android:onClick "
                        + "attribute defined on view " + mHostView.javaClass + idText
            )
        }

    }

}