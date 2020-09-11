package com.luzeping.customthemedemo.skin.app

import android.content.Context
import android.util.AttributeSet
import android.view.View

interface SkinLayoutInflater {

    fun createView(context: Context, name: String, attrs: AttributeSet): View?

}