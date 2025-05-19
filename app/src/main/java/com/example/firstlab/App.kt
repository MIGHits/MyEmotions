package com.example.firstlab

import android.app.Application
import com.example.firstlab.di.appModule
import com.example.firstlab.di.dataModule
import com.example.firstlab.di.domainModule
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId

class App : Application() {
    companion object {
        lateinit var app: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        AndroidThreeTen.init(this)
        startKoin {
            androidContext(this@App)
            modules(appModule, dataModule, domainModule)
            androidLogger(Level.DEBUG)
        }
    }
}