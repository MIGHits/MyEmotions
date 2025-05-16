package com.example.firstlab

import android.app.Application
import com.example.firstlab.di.appModule
import com.example.firstlab.di.dataModule
import com.example.firstlab.di.domainModule
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext

class App : Application() {
    companion object {
        lateinit var app: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        startKoin {
            androidContext(this@App)
            modules(appModule, dataModule, domainModule)
        }
    }
}