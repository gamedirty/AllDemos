package com.sovnem.startupdemo

import android.app.Application

interface InitWork {
    fun init(app: Application)
    fun dependencies(): List<Class<out InitWork>>? {
        return null
    }

    fun shouldOnUIThread(): Boolean = false
}