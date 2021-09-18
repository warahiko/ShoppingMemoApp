package io.github.warahiko.shoppingmemoapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.github.warahiko.shoppingmemoapp.error.ErrorMonitor
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class ShoppingMemoApplication : Application() {

    @Inject
    lateinit var errorMonitor: ErrorMonitor

    override fun onCreate() {
        super.onCreate()

        configureTimber()

        registerActivityLifecycleCallbacks(errorMonitor)
    }

    private fun configureTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun onTerminate() {
        unregisterActivityLifecycleCallbacks(errorMonitor)
        super.onTerminate()
    }
}
