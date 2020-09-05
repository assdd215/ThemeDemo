package com.luzeping.customthemedemo.skin.res

class SkinCompatResources {

    companion object {
        fun getInstance() = INSTANCE.instance
    }

    private object INSTANCE {
        val instance = SkinCompatResources()
    }

}

interface SkinResources {
    fun clear()
}