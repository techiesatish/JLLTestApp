package com.jll.jlltestapp.utils

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JLLTestApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}