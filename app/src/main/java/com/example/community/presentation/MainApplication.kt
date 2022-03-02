package com.example.community.presentation

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.community.Utilities.Prefs

class MainApplication: Application() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    companion object {
        lateinit var instance: MainApplication
    }

    val prefs: Prefs by lazy {
        Prefs(instance)
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}