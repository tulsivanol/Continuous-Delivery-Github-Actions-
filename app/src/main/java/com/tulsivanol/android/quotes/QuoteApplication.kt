package com.tulsivanol.android.quotes

import android.app.Application
import timber.log.Timber

class QuoteApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}