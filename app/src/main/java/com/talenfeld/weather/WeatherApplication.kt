package com.talenfeld.weather

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.talenfeld.weather.core.navigation.ActivityNavigator
import com.talenfeld.weather.main.di.MainProviderImpl

class WeatherApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        val navigator = ActivityNavigator()
        registerNavigatorLifecycle(navigator)

        COMPONENTS = Components(
            MainProviderImpl(
                context = baseContext,
                navigator = navigator,
                sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            )
        )
    }

    private fun registerNavigatorLifecycle(navigator: ActivityNavigator) {
        registerActivityLifecycleCallbacks(
            object : ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    // do nothing
                }

                override fun onActivityStarted(activity: Activity) {
                    navigator.attach(activity as AppCompatActivity)
                }

                override fun onActivityResumed(activity: Activity) {
                    // do nothing
                }

                override fun onActivityPaused(activity: Activity) {
                    // do nothing
                }

                override fun onActivityStopped(activity: Activity) {
                    navigator.detach()
                }

                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                    // do nothing
                }

                override fun onActivityDestroyed(activity: Activity) {
                    // do nothing
                }
            }
        )
    }

    companion object {
        lateinit var COMPONENTS: Components

        private const val SHARED_PREFS_NAME = "default"
    }
}
