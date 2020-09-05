package com.luzeping.customthemedemo.skin

open class SkinObservable {

    private val observers = ArrayList<SkinObserver>()

    @Synchronized fun addObserver(observer: SkinObserver) {

        if (observers.contains(observer).not()) {
            observers.add(observer)
        }
    }

    @Synchronized fun deleteObserver(observer: SkinObserver) {
        observers.remove(observer)
    }

    fun notifyUpdateSkin(arg: Any? = null) {
        val arrLocal: Array<SkinObserver> = synchronized(this) {
            observers.toArray(arrayOfNulls(observers.size))
        }
        for (i in arrLocal.size - 1 downTo 0) {
            arrLocal[i].updateSkin(this, arg)
        }
    }

}

interface SkinObserver {
    fun updateSkin(observable: SkinObservable?, o: Any?)
}