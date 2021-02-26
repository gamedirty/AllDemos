package com.sovnem.startupdemo.works

import android.app.Application
import android.util.Log
import com.sovnem.startupdemo.InitWork
import java.util.concurrent.TimeUnit

object F : InitWork {
    override fun init(app: Application) {
        TimeUnit.MILLISECONDS.sleep(200)
        Log.i("InitWork", "初始化完成：" + this.javaClass.name)
    }
}