package org.mall.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.mall.app.di.AppModule

class MallApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MallApp)
            androidLogger(
                level = Level.INFO
            )
            modules(AppModule)
            createEagerInstances()
        }
    }
}