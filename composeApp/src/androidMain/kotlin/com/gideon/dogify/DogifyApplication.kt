package com.gideon.dogify

import android.app.Application
import com.gideon.dogify.di.initKoin

class DogifyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}