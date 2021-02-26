package com.sovnem.startupdemo

import android.app.Application
import android.util.ArrayMap
import android.util.Log
import com.sovnem.startupdemo.works.*
import java.util.concurrent.*
import kotlin.system.measureTimeMillis

object AppStarter {
    private val done by lazy { CopyOnWriteArraySet<Class<out InitWork>>() }

    private val executings by lazy { ConcurrentHashMap<Class<out InitWork>, Future<*>>() }

    private val workMap by lazy { ArrayMap<Class<out InitWork>, InitWork>() }

    fun start(application: Application, initWorks: List<InitWork>) {
        initWorks.forEach {
            workMap[it.javaClass] = it
        }
        showMeasure("总耗时") {
            val countDownLatch = CountDownLatch(initWorks.size)
            initWorks.forEach {
                execute(application, it, countDownLatch)
            }
            countDownLatch.await()
        }
    }

    private fun execute(application: Application, it: InitWork, countDownLatch: CountDownLatch) {
        val dependencies = it.dependencies()?.filter {
            !done.contains(it)
        }
        val workClass = it.javaClass
        if (dependencies.isNullOrEmpty()) {
            if (done.contains(workClass)) {
                countDownLatch.countDown()
                return
            }
            executings[workClass]?.let {
                it.get()
                countDownLatch.countDown()
                done.add(workClass)
                return
            }

            executings[workClass] = AppExecutors.getInitialExecutor().submit {
                if (it.shouldOnUIThread()) {
                    AppExecutors.getMainThreadExecutor().execute {
                        showMeasure(it.javaClass.name) {
                            it.init(application)
                        }
                        countDownLatch.countDown()
                        done.add(workClass)
                    }
                } else {
                    showMeasure(it.javaClass.name) {
                        it.init(application)
                    }
                    countDownLatch.countDown()
                    done.add(workClass)
                }
            }
        } else {
            val depCountDownLatch = CountDownLatch(dependencies.size)
            dependencies.forEach {
                workMap[it]?.let { it1 ->
                    execute(application, it1, depCountDownLatch)
                    return@forEach
                }
                depCountDownLatch.countDown()
            }
            depCountDownLatch.await()
            execute(application, it, countDownLatch)
        }
    }


    private val initworks by lazy { arrayOf(A, B, C, D, E, F) }

    fun init(application: Application) {
        start(application, initworks.asList())
    }

    fun showMeasure(name: String, action: () -> Unit) {
        val cost = measureTimeMillis {
            action.invoke()
        }
        Log.i("AppStarter", name + "耗时：" + cost)
    }
}