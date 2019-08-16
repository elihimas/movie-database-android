package com.elihimas.moviedatabase

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Before
import org.mockito.MockitoAnnotations
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

abstract class PresentersTest {

    private companion object {
        const val language = "en"
        const val country = "US"
    }

    @Before
    open fun setup() {
        MockitoAnnotations.initMocks(this)

        Locale.setDefault(Locale(language, country))

        setupSchedulers()
    }

    private fun setupSchedulers() {
        val immediate = object : Scheduler() {

            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                // this prevents StackOverflowErrors when scheduling with a delay
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, true)
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }
}