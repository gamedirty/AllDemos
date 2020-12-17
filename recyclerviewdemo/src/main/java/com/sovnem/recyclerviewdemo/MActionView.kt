package com.sovnem.recyclerviewdemo

import androidx.lifecycle.*

class MActionView : LifecycleOwner {
    private val registry by lazy { LifecycleRegistry(this) }
    override fun getLifecycle(): Lifecycle {
        return registry
    }

    fun doSth() {

        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {

            }

        })

    }
}

