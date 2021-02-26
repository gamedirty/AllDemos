package com.sovnem.startupdemo

import android.os.Build
import android.os.Handler
import android.os.Looper
import java.lang.reflect.InvocationTargetException
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger


/**
 * app内统一的线程池管理器
 */
object AppExecutors : CommandExecutor() {

    private val mDefaultTaskExecutor = DefaultCommandExecutor()
    private var mDelegate: CommandExecutor = mDefaultTaskExecutor

    private val sMainThreadExecutor =
        Executor { command -> postToMainThread(command) }

    private val sIOThreadExecutor =
        Executor { command -> executeOnDiskIO(command) }

    private val mInitialExecutor by lazy {
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
    }

    fun setDelegate(taskExecutor: CommandExecutor?) {
        mDelegate = taskExecutor ?: mDefaultTaskExecutor
    }

    fun getMainThreadExecutor(): Executor {
        return sMainThreadExecutor
    }

    fun getIOThreadExecutor(): Executor {
        return sIOThreadExecutor
    }

    fun getInitialExecutor(): ExecutorService {
        return mInitialExecutor
    }

    override fun executeOnDiskIO(runnable: Runnable) {
        mDelegate.executeOnDiskIO(runnable)
    }

    override fun postToMainThread(runnable: Runnable) {
        mDelegate.postToMainThread(runnable)
    }

    override fun isMainThread(): Boolean {
        return mDelegate.isMainThread()
    }

    override fun <T> submit(callable: Callable<T>): Future<T> {
        return mDelegate.submit(callable)
    }


}

abstract class CommandExecutor {
    abstract fun executeOnDiskIO(runnable: Runnable)
    abstract fun postToMainThread(runnable: Runnable)
    fun executeOnMainThread(runnable: Runnable) {
        if (isMainThread()) {
            runnable.run()
        } else {
            postToMainThread(runnable)
        }
    }

    abstract fun isMainThread(): Boolean
    abstract fun <T> submit(callable: Callable<T>): Future<T>
}

class DefaultCommandExecutor : CommandExecutor() {
    private val mLock = Any()
    private val mDiskIO = Executors.newFixedThreadPool(4, object : ThreadFactory {
        val THREAD_NAME_STEM = "tsl_disk_io_%d"
        private val mThreadId = AtomicInteger(0)
        override fun newThread(r: Runnable): Thread {
            return Thread(r).apply {
                name = String.format(THREAD_NAME_STEM, mThreadId.getAndIncrement())
            }
        }
    })

    @Volatile
    private var mMainHandler: Handler? = null
    override fun executeOnDiskIO(runnable: Runnable) {
        mDiskIO.execute(runnable)
    }

    override fun postToMainThread(runnable: Runnable) {
        if (mMainHandler == null) {
            synchronized(mLock) {
                if (mMainHandler == null) {
                    mMainHandler = createAsync(Looper.getMainLooper())
                }
            }
        }
        mMainHandler!!.post(runnable)
    }

    override fun isMainThread(): Boolean {
        return Looper.getMainLooper().thread === Thread.currentThread()
    }

    override fun <T> submit(callable: Callable<T>): Future<T> {
        return mDiskIO.submit(callable)
    }

    companion object {
        private fun createAsync(looper: Looper): Handler {
            if (Build.VERSION.SDK_INT >= 28) {
                return Handler.createAsync(looper)
            }
            try {
                return Handler::class.java.getDeclaredConstructor(
                    Looper::class.java, Handler.Callback::class.java,
                    Boolean::class.javaPrimitiveType
                )
                    .newInstance(looper, null, true)
            } catch (ignored: IllegalAccessException) {
            } catch (ignored: InstantiationException) {
            } catch (ignored: NoSuchMethodException) {
            } catch (e: InvocationTargetException) {
                return Handler(looper)
            }
            return Handler(looper)
        }
    }
}

