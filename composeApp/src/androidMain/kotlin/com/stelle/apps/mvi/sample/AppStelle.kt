package com.stelle.apps.mvi.sample

import android.app.Application
import com.stelle.apps.mvi.sample.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class AppStelle : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@AppStelle)
        }
    }
}