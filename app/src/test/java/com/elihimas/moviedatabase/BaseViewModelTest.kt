package com.elihimas.moviedatabase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.rules.TestRule
import java.util.*
import java.util.concurrent.TimeUnit

abstract class BaseViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    companion object {
        private const val LANGUAGE = "en"
        private const val COUNTRY = "US"

        @BeforeClass
        @JvmStatic
        fun setupClass() {
            Locale.setDefault(Locale(LANGUAGE, COUNTRY))

            val immediate = object : Scheduler() {

                override fun scheduleDirect(
                    run: Runnable,
                    delay: Long,
                    unit: TimeUnit
                ): Disposable {
                    return super.scheduleDirect(run, 0, unit)
                }

                override fun createWorker(): Worker {
                    return ExecutorScheduler.ExecutorWorker({ it.run() }, true)
                }
            }

            RxJavaPlugins.setInitIoSchedulerHandler { immediate }
            RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
            RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
            RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
        }
    }
}